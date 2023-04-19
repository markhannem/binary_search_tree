package org.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // Create a new binary search tree
        BinarySearchTree bst = new BinarySearchTree();
        // Create a new scanner
        Scanner scanner = new Scanner(System.in);

        // Read in a series of numbers and insert them into the tree
        System.out.print("Enter a series of numbers: ");
        String input = scanner.nextLine();
        String[] tokens = input.split("\\s+");

        for (String token : tokens) {
            int value = Integer.parseInt(token);
            bst.insert(value);
        }

        // Display the tree
        bst.display();

        // Read in a series of commands and execute them
        boolean running = true;
        while (running) {
            System.out.print("Enter 'add' to add a node, 'delete' to delete a node, 'balance' to balance the tree, or 'quit' to quit: ");
            String choice = scanner.nextLine();

            switch (choice) {
                // Read in a value to add and add it to the tree
                case "add":
                    System.out.print("Enter a value to add: ");
                    int valueToAdd = scanner.nextInt();
                    bst.insert(valueToAdd);
                    bst.display();
                    scanner.nextLine(); // Consume the newline character left by nextInt()
                    break;
                // Read in a value to delete and delete it from the tree
                case "delete":
                    System.out.print("Enter a value to delete: ");
                    int valueToDelete = scanner.nextInt();
                    boolean deleted = bst.delete(valueToDelete);
                    if (deleted) {
                        bst.display();
                    } else {
                        System.out.println("Node not found");
                    }
                    scanner.nextLine(); // Consume the newline character left by nextInt()
                    break;

                case "balance":
                    bst.balance();
                    bst.display();
                    break;

                case "quit":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }

        scanner.close();
    }
}
