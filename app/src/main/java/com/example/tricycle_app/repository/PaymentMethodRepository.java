package com.example.tricycle_app.repository;

import android.content.Context;
import com.example.tricycle_app.model.PaymentMethod;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodRepository {

    private static final String FILE_NAME = "payment_methods.txt";
    private Context context;

    public PaymentMethodRepository(Context context) {
        this.context = context;
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        List<PaymentMethod> methods = new ArrayList<>();
        File internalFile = new File(context.getFilesDir(), FILE_NAME);

        // 1. If internal file doesn't exist, load from ASSETS (First Run)
        if (!internalFile.exists()) {
            methods = loadFromAssets();
            savePaymentMethods(methods); // Save to internal storage so we can edit later
            return methods;
        }

        // 2. If internal file exists, load from THERE (Subsequent Runs)
        try (BufferedReader br = new BufferedReader(new FileReader(internalFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                PaymentMethod pm = PaymentMethod.fromString(line);
                if (pm != null) {
                    methods.add(pm);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return methods;
    }

    private List<PaymentMethod> loadFromAssets() {
        List<PaymentMethod> list = new ArrayList<>();
        try (InputStream is = context.getAssets().open(FILE_NAME);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                PaymentMethod pm = PaymentMethod.fromString(line);
                if (pm != null) {
                    list.add(pm);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void savePaymentMethods(List<PaymentMethod> methods) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(context.getFilesDir(), FILE_NAME)))) {
            for (PaymentMethod pm : methods) {
                bw.write(pm.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdatePaymentMethod(PaymentMethod newMethod) {
        List<PaymentMethod> methods = getAllPaymentMethods();
        boolean found = false;
        for (int i = 0; i < methods.size(); i++) {
            if (methods.get(i).getId().equals(newMethod.getId())) {
                methods.set(i, newMethod);
                found = true;
                break;
            }
        }
        if (!found) {
            methods.add(newMethod);
        }
        savePaymentMethods(methods);
    }

    // NEW DELETE METHOD
    public void deletePaymentMethod(String id) {
        List<PaymentMethod> methods = getAllPaymentMethods();
        List<PaymentMethod> updatedMethods = new ArrayList<>();

        for (PaymentMethod pm : methods) {
            if (!pm.getId().equals(id)) {
                updatedMethods.add(pm);
            }
        }

        savePaymentMethods(updatedMethods);
    }
}