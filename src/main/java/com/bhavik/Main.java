package com.bhavik;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ExchangeRateService service = new ExchangeRateService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Currency Converter");
        System.out.print("Enter base currency (e.g. USD): ");
        String base = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter target currency (e.g. INR): ");
        String target = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        double rate = service.getExchangeRate(base, target);
        if (rate > 0) {
            System.out.printf("%.2f %s = %.2f %s%n",
                    amount, base,
                    amount * rate, target);
        } else {
            System.out.println("Failed to get exchange rate");
        }
    }
}