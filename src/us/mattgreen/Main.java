package us.mattgreen;

import java.util.Scanner;

public class Main {

    private static String line="";
    private static String lineRating = "";

    private final static FileInput cardAccts = new FileInput("movie_cards.csv");
    private final static FileInput cardPurchases = new FileInput("movie_purchases.csv");
    private final static FileInput cardRatings = new FileInput("movie_rating.csv");
    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        String line;
        String[] fields;

        String lineRating;
        String[] ratingFields;
        String tmpLine;

        int[] nums = new int[2];
        int[] ratingNums = new int[2];

        boolean first = true;
        System.out.format("%8s  %-18s %6s %6s %4s\n", "Account", "Name", "Movies", "Points", "Ratings");
        while ((line = cardAccts.fileReadLine()) != null) {
            //if ((lineRating = cardRatings.fileReadLine()) != null) {
                fields = line.split(",");
                //ratingFields = lineRating.split(",");


                findPurchases(first, fields[0], nums);
                findRating(first, fields[0], ratingNums);
                first = false;
                if (ratingNums[0] == 0) {
                    System.out.format("00%6s  %-18s  %2d   %4d   %4d\n", fields[0], fields[1], nums[0], nums[1], (ratingNums[1]));
                }
                else if (ratingNums[0] > 0) {
                    System.out.format("00%6s  %-18s  %2d   %4d   %.2f\n", fields[0], fields[1], nums[0], nums[1], ((double)ratingNums[1] / (double)ratingNums[0]));
                }
            //}
        }
    }

    public static void findPurchases(boolean first, String acct, int[] nums) {
        nums[0] = 0;
        nums[1] = 0;
        String[] fields;
        boolean done = false;

        if (first) {
            line = cardPurchases.fileReadLine();
        }
        while ((line != null) && !(done)) {
            fields = line.split(",");
            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                nums[0]++;
                nums[1] += Integer.parseInt(fields[2]);
                line = cardPurchases.fileReadLine();
            }
        }
    }

    public static void findRating(boolean first, String acct, int[] ratingNums) {
        ratingNums[0] = 0;
        ratingNums[1] = 0;
        String[] fields;
        boolean done = false;

        if (first) {
            lineRating = cardRatings.fileReadLine();

        }
        while ((lineRating != null) && !(done)) {
            fields = lineRating.split(",");
            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                ratingNums[0]++;
                ratingNums[1] += (Integer.parseInt(fields[1]));
                lineRating = cardRatings.fileReadLine();
            }
        }
    }



}