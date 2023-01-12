import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
public class ProductManagement {

    static ArrayList<Product> products = new ArrayList<>();
    static ArrayList<WholeSalers> wholesalers = new ArrayList<WholeSalers>();
    static ArrayList<Retailers> retailers = new ArrayList<Retailers>();

    static ArrayList<Product> w_products = new ArrayList<>();
    static ArrayList<Product> r_products = new ArrayList<>();

    public static void main(String[] args) {

        try {
            Scanner sc1 = new Scanner(new File("/Users/archanadevi/IdeaProjects/Day1MainTask/src/products.csv"));
            Scanner sc2 = new Scanner(new File("/Users/archanadevi/IdeaProjects/Day1MainTask/src/wholesalers.csv"));
            Scanner sc3 = new Scanner(new File("/Users/archanadevi/IdeaProjects/Day1MainTask/src/retailers.csv"));

            sc1.useDelimiter(";");
            while (sc1.hasNext()) {
//                System.out.println(sc1.next());
                String[] prod_data = sc1.next().split(",", 4);
                products.add(new Product(prod_data[0], prod_data[1], prod_data[2], prod_data[3]));
            }
            sc1.close();

            sc2.useDelimiter(";");
            while (sc2.hasNext()) {
//                System.out.println(sc2.next());
                String[] wholesalers_data = sc2.next().split(",", 2);
                wholesalers.add(new WholeSalers(wholesalers_data[0], wholesalers_data[1]));
            }
            sc2.close();

            sc3.useDelimiter(";");
            while (sc3.hasNext()) {
//                System.out.println(sc3.next());
                String[] retailers_data = sc3.next().split(",", 2);
                retailers.add(new Retailers(retailers_data[0], retailers_data[1]));
            }
            sc3.close();

            System.out.println("Enter the option: ");
            System.out.println("1. View products");
            System.out.println("2. View wholesalers");
            System.out.println("3. View retailers");
            System.out.println("4. Allocate product to wholesaler");
            System.out.println("5. Allocate wholesaler to retailer");
            System.out.println("6. Quit");


            Scanner sc = new Scanner(System.in);

            while(true) {

                System.out.println("Enter your option: ");
                int opt = sc.nextInt();

                switch (opt) {
                    case 1:
                        Product.viewProducts();
                        break;
                    case 2:
                        WholeSalers.viewWholeSalers();
                        break;
                    case 3:
                        Retailers.viewRetailers();
                        break;
                    case 4:
                        System.out.println("Enter the product id you want to purchase: ");
                        String w_req_id = sc.next();
                        System.out.println("Enter the stock you want to purchase: ");
                        String w_req_stock = sc.next();
                        WholeSalers.allocateFromProduct(w_req_id, w_req_stock, products);
                        break;
                    case 5:
                        System.out.println("Enter the wholesaler you want to purchase from: ");
                        System.out.println("Enter the product id you want to purchase: ");
                        String r_req_id = sc.next();
                        System.out.println("Enter the stock you want to purchase: ");
                        String r_req_stock = sc.next();
                        Retailers.allocateFromWholeSaler(r_req_id, r_req_stock, products);
                        break;
                    case 6:
                        System.out.println("Program ended");
                        return;
                }
            }


        }
        catch (Exception e) {
            System.out.println("Exception: " + e + e.getMessage());
        }
    }

}
class Product {
    String id;
    String name;
    String price;
    static String p_stock="0";

    Product(String id, String name, String price, String stock) {
        this.id=id;
        this.name=name;
        this.price=price;
        this.p_stock=stock;
    }

    Product() {
    }

    static void viewProducts() {
        System.out.println("Products:");
        System.out.println("ID\t Name\t\tPrice\tStock");
        for (Product product :  ProductManagement.products) {
            System.out.print(product.id + "\t" + product.name + "\t" + product.price + "\t" + product.p_stock + "\n");
        }
    }
}

class WholeSalers extends Product {
    String id;
    String name;
    String w_stock;

    WholeSalers(String id, String name) {
        this.id=id;
        this.name=name;
        this.w_stock = "0";
    }

    static void viewWholeSalers() {
        System.out.println("WholeSalers:");
        System.out.println("ID\t Name\t\t\t Products");
        for (WholeSalers wholesaler : ProductManagement.wholesalers) {
            System.out.print(wholesaler.id + "\t" + wholesaler.name + "\t\t" + ProductManagement.w_products.get(0).id + " " + p_stock + "\n");
        }
//        WholeSalers.allocateFromProduct("1", "20", ProductManagement.products);
    }

    static void allocateFromProduct(String w_req_id, String w_req_stock, ArrayList<Product> products) {

        for(Product product : products)
        {
            if(product.id.equals(w_req_id)) {
                if(Integer.parseInt(product.p_stock) >= Integer.parseInt(w_req_stock)) {

                    ProductManagement.w_products.add(product);
                    product.p_stock = String.valueOf(Integer.parseInt(product.p_stock) - Integer.parseInt(w_req_stock));
                    viewWholeSalers();
                    System.out.println("in line 154" + p_stock);
                    p_stock = String.valueOf(Integer.parseInt(p_stock) + Integer.parseInt(w_req_stock));
                    System.out.println("in line 145" + p_stock);
                    return;
                }
                else {
                    System.out.println("Stock not available.");
                }
                viewProducts();
            }
        }
    }
}

class Retailers extends Product {
    String id;
    String name;

    Retailers(String id, String name) {
        this.id=id;
        this.name=name;
    }

    static void viewRetailers() {
        System.out.println("Retailers:");
        System.out.println("ID\t Name");
        for (Retailers retailer : ProductManagement.retailers) {
            System.out.print(retailer.id + "\t" + retailer.name + "\n");
        }
    }

    static void allocateFromWholeSaler(String r_req_id, String r_req_stock, ArrayList<Product> products) {

        for(Product product : products)
        {
            if(product.id.equals(r_req_id)) {
                if(Integer.parseInt(product.p_stock) >= Integer.parseInt(r_req_stock)) {

                    ProductManagement.w_products.add(product);
                    product.p_stock = String.valueOf(Integer.parseInt(product.p_stock) - Integer.parseInt(w_req_stock));
                    viewWholeSalers();
                    return;
                }
                else {
                    System.out.println("Stock not available.");
                }
            }
        }
    }
}