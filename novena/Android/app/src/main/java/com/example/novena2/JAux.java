package com.example.novena2;

public class JAux {

    public static String convertString(String input) {
        String[] values = input.split("\n");
        StringBuilder output = new StringBuilder();

        for (String value : values) {
            if (value.trim().equals("true")) {
                output.append("green\n");
            } else if (value.trim().equals("false")) {
                output.append("null\n");
            }
        }

        return output.toString().trim();
    }
}
