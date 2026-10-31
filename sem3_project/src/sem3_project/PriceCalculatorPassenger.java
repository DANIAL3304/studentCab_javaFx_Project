package sem3_project;

public class PriceCalculatorPassenger {
    
    // Price matrix for 9 locations
    private static final double[][] priceMap = {
        {0, 5, 7, 10, 12, 15, 18, 20, 22}, // KC
        {5, 0, 3, 6, 9, 12, 15, 18, 20},   // KTDI
        {7, 3, 0, 5, 8, 11, 14, 17, 19},   // KPZ
        {10, 6, 5, 0, 4, 7, 10, 13, 15},   // K12
        {12, 9, 8, 4, 0, 3, 6, 9, 11},     // PFC
        {15, 12, 11, 7, 3, 0, 4, 7, 9},    // IOI CITY MALL
        {18, 15, 14, 10, 6, 4, 0, 3, 5},   // RESTAURANT PKS
        {20, 18, 17, 13, 9, 7, 3, 0, 2},   // SULTAN ABDUL SAMAD LIBRARY
        {22, 20, 19, 15, 11, 9, 5, 2, 0}   // MRT UPM
    };

    public static double calculatePrice(String fromLocation, String toLocation, String paxCount) {
        // Handle invalid location inputs
        if (fromLocation == null || toLocation == null || paxCount == null) {
            return -1;  // Invalid locations or pax count
        }

        // Mapping locations to their corresponding index values
        int fromIndex = getLocationIndex(fromLocation);
        int toIndex = getLocationIndex(toLocation);

        if (fromIndex >= 0 && toIndex >= 0) {
            // Get the base price
            double basePrice = priceMap[fromIndex][toIndex];

            // Multiply by the number of passengers (paxCount)
            int pax = Integer.parseInt(paxCount);
            double totalPrice = basePrice * pax;

            return totalPrice;
        }

        return -1; // Return -1 for invalid locations
    }

    private static int getLocationIndex(String location) {
        switch (location) {
            case "KC": return 0;
            case "KTDI": return 1;
            case "KPZ": return 2;
            case "K12": return 3;
            case "PFC": return 4;
            case "IOI CITY MALL": return 5;
            case "RESTAURANT PKS": return 6;
            case "SULTAN ABDUL SAMAD LIBRARY": return 7;
            case "MRT UPM": return 8;
            default: return -1;
        }
    }
}
