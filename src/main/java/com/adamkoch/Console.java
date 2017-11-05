package com.adamkoch;

import java.util.Scanner;

/**
 * <p>Created by aakoch on 2017-11-04.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class Console {

    Scanner scanner = new Scanner(System.in);

    public String prompt(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
}
