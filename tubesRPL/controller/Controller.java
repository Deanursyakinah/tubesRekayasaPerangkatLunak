package controller;

import java.sql.Statement;
import java.time.Instant;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import model.Account;
import model.AccountStatus;
import model.Admin;
import model.DLC;
import model.Game;
import model.Item;
import model.ItemStatus;
import model.Publisher;
import model.Review;
import model.ShoppingCart;
import model.Transaction;
import model.User;

public class Controller {
    private static Controller instance;

    static DatabaseHandler conn = new DatabaseHandler();

    public Controller() {

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    
    public Account getAccount(String username, String password) {
        conn.connect();
        String queryUser = "SELECT * FROM users WHERE username = ? AND password = ?";
        String queryAdmin = "SELECT * FROM admin WHERE username = ? AND password = ?";
        String queryPublisher = "SELECT * FROM publisher WHERE username = ? AND password = ?";

        Account account = null;

        try {
            // Check for user
            PreparedStatement stmtUser = conn.con.prepareStatement(queryUser);
            stmtUser.setString(1, username);
            stmtUser.setString(2, password);
            ResultSet rsUser = stmtUser.executeQuery();

            if (rsUser.next()) {
                User user = new User();
                user.setId(rsUser.getInt("user_id"));
                user.setName(rsUser.getString("username"));
                user.setPassword(rsUser.getString("password"));
                user.setStatus(AccountStatus.valueOf(rsUser.getString("user_status")));
                user.setWallet(rsUser.getDouble("wallet"));
                account = user;
            }

            // Check for admin if not found
            if (account == null) {
                PreparedStatement stmtAdmin = conn.con.prepareStatement(queryAdmin);
                stmtAdmin.setString(1, username);
                stmtAdmin.setString(2, password);
                ResultSet rsAdmin = stmtAdmin.executeQuery();

                if (rsAdmin.next()) {
                    Admin admin = new Admin();
                    admin.setId(rsAdmin.getInt("admin_id"));
                    admin.setName(rsAdmin.getString("username"));
                    admin.setPassword(rsAdmin.getString("password"));
                    admin.setStatus(AccountStatus.valueOf(rsAdmin.getString("admin_status")));
                    account = admin;
                }
            }

            // Check for publisher if not found
            if (account == null) {
                PreparedStatement stmtPublisher = conn.con.prepareStatement(queryPublisher);
                stmtPublisher.setString(1, username);
                stmtPublisher.setString(2, password);
                ResultSet rsPublisher = stmtPublisher.executeQuery();

                if (rsPublisher.next()) {
                    Publisher publisher = new Publisher();
                    publisher.setId(rsPublisher.getInt("publisher_id"));
                    publisher.setName(rsPublisher.getString("username"));
                    publisher.setPassword(rsPublisher.getString("password"));
                    publisher.setStatus(AccountStatus.valueOf(rsPublisher.getString("publisher_status")));
                    account = publisher;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn.disconnect();
        return account;
    }

    // INSERT (punya user)
    public boolean insertNewUser(User user) {
        conn.connect();
        String query = "INSERT INTO users VALUES(?,?,?,?,?)";
        try (PreparedStatement stmt = conn.con.prepareStatement(query)) {
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getStatus().toString());
            stmt.setDouble(5, user.getWallet());
            
            int rowsAffected = stmt.executeUpdate();
            //mengevaluasi apakah operasi insert berhasil atau tidak 
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }
    }

    // INSERT (punya item = game)
    public boolean insertNewGame(Game game, Publisher publisher) {
        conn.connect();
        String query = "INSERT INTO item VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, game.getItemID());
            stmt.setString(2, game.getName());
            stmt.setString(3, "Game");
            stmt.setDouble(4, game.getPrice());
            stmt.setString(5, game.getDescription());
            stmt.setInt(6, publisher.getId());

            // buat handle null status 
            String statusString = (game.getStatus() != null) ? game.getStatus().toString() : "AVAILABLE";
            stmt.setString(7, statusString);

            int rowsAffected = stmt.executeUpdate();

            // evaluasi buat kalau operasi insertnya berhasil
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }
    }

    // INSERT (punya item = dlc)
    public boolean insertNewDLC(DLC game, Publisher publisher) {
        conn.connect();
        String query = "INSERT INTO item VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, game.getItemID());
            stmt.setString(2, game.getName());
            stmt.setString(3, "DLC");
            stmt.setDouble(4, game.getPrice());
            stmt.setString(5, game.getDescription());
            stmt.setInt(6, publisher.getId());

            String statusString = (game.getStatus() != null) ? game.getStatus().toString() : "AVAILABLE";
            stmt.setString(7, statusString);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }
    }

    // update wallet user
    public boolean updateWallet(User user, double topUpAmount) {
        conn.connect();
        double currentWalletAmount = user.getWallet();
        double updatedWalletAmount = currentWalletAmount + topUpAmount;
        String query = "UPDATE users SET wallet = " + updatedWalletAmount + " WHERE user_id = " + user.getId();
        try {
            Statement stmt = conn.con.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }
    }

    public ArrayList<User> getUserList() {
        conn.connect();
        String query = "SELECT * FROM users WHERE user_status = 'NOT_BANNED'";
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setStatus(AccountStatus.valueOf(rs.getString("user_status")));
                user.setWallet(rs.getDouble("wallet"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<User> getAllUserList() {
        conn.connect();
        String query = "SELECT * FROM users";
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setStatus(AccountStatus.valueOf(rs.getString("user_status")));
                user.setWallet(rs.getDouble("wallet"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // UPDATE status jadi banned
    public boolean updateStatusUser(int id) {
        conn.connect();
        String query = "UPDATE users SET user_status= 'BANNED'"
                + "WHERE user_id='" + id + "'";
        try {
            Statement stmt = conn.con.createStatement();
            stmt.executeUpdate(query);
            return (true);
        } catch (SQLException e) {
            e.printStackTrace();
            return (false);
        }
    }

    public ArrayList<User> getUserBanned() {
        conn.connect();
        String query = "SELECT * FROM users WHERE user_status= 'BANNED'";
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setStatus(AccountStatus.valueOf(rs.getString("user_status")));
                user.setWallet(rs.getDouble("wallet"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<Game> getGames() {
        conn.connect();
        String query = "SELECT * FROM item WHERE type = 'Game'";
        ArrayList<Game> games = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Game game = new Game();
                game.setItemID(rs.getInt("item_id"));
                game.setName(rs.getString("name"));
                game.setType(rs.getString("type"));
                game.setDescription(rs.getString("description"));
                game.setPrice(rs.getInt("price"));
                game.setPublisherID(rs.getInt("publisher_id"));

                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString);
                game.setStatus(status);

                // Handling DLC
                ArrayList<DLC> dlcList = getDLCs(game);
                game.setDLC(dlcList);

                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return games;
    }
    
    public ArrayList<Review> getReviewsForGame(Game game) {
        conn.connect();
        String query = "SELECT * FROM review WHERE item_id = " + game.getItemID();
        ArrayList<Review> reviews = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Review review = new Review();
                review.setReviewID(rs.getInt("review_id"));

                // Set the associated game
                review.setItem(game);

                // Fetch the associated user (you need to implement getUserById method)
                User user = getUserById(rs.getInt("user_id"));
                review.setUser(user);

                // Set the review text
                review.setReviewText(rs.getString("comment"));

                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return reviews;
    }

    public ArrayList<Review> getReviewsForDLC(DLC dlc) {
        conn.connect();
        String query = "SELECT * FROM review WHERE item_id = " + dlc.getItemID();
        ArrayList<Review> reviews = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Review review = new Review();
                review.setReviewID(rs.getInt("review_id"));

                // Set the associated DLC
                review.setItem(dlc);

                // Fetch the associated user (you need to implement getUserById method)
                User user = getUserById(rs.getInt("user_id"));
                review.setUser(user);

                // Set the review text
                review.setReviewText(rs.getString("comment"));

                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return reviews;
    }

    public ArrayList<Game> getPublishedGames(Publisher publisher) {
        conn.connect();
        String query = "SELECT * FROM item WHERE type = 'Game' AND publisher_id = " + publisher.getId();
        ArrayList<Game> games = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Game game = new Game();
                game.setItemID(rs.getInt("item_id"));
                game.setName(rs.getString("name"));
                game.setType(rs.getString("type"));
                game.setDescription(rs.getString("description"));
                game.setPrice(rs.getInt("price"));
                game.setPublisherID(rs.getInt("publisher_id"));

                //handle item status 
                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString);
                game.setStatus(status);

                ArrayList<DLC> dlcList = getDLCs(game); 
                game.setDLC(dlcList);

                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); // tutup koneksi kalau sudah
        }

        return games;
    }

    public ArrayList<DLC> getPublishedDLC(Publisher publisher) {
        conn.connect();
        String query = "SELECT * FROM item WHERE type = 'DLC' AND publisher_id = " + publisher.getId();
        ArrayList<DLC> games = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                DLC game = new DLC();
                game.setItemID(rs.getInt("item_id"));
                game.setName(rs.getString("name"));
                game.setType(rs.getString("type"));
                game.setDescription(rs.getString("description"));
                game.setPrice(rs.getInt("price"));
                game.setPublisherID(rs.getInt("publisher_id"));

                // Handling the ItemStatus enum
                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString); 
                game.setStatus(status);

                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); 
        }

        return games;
    }

    public ArrayList<DLC> getDLCs(Game game) {
        conn.connect();
        String query = "SELECT * FROM item i JOIN game_dlc_relation g ON g.game_id = '" + game.getItemID()
                + "' WHERE i.type = 'DLC'";
        ArrayList<DLC> dlcs = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                DLC dlc = new DLC();
                dlc.setItemID(rs.getInt("id"));
                dlc.setName(rs.getString("name"));
                dlc.setType(rs.getString("type"));
                dlc.setDescription(rs.getString("description"));
                dlc.setPrice(rs.getInt("price"));
                dlc.setPublisherID(rs.getInt("publisher_id"));
                
                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString); 
                dlc.setStatus(status);

                dlcs.add(dlc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); 
        }

        return dlcs;
    }

    public User getUserById(int userId) {
        conn.connect();
        String query = "SELECT * FROM users WHERE user_id = " + userId;

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String name = rs.getString("username");
                String password = rs.getString("password");
                int id = rs.getInt("user_id");

                return new User(name, password, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        // return null kalau user tidak ada 
        return null;
    }

    public void getGameDetails(Game game) {
        conn.connect();
        String query = "SELECT * FROM item WHERE item_id = " + game.getItemID();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                game.setItemID(rs.getInt("item_id"));
                game.setName(rs.getString("name"));
                game.setType(rs.getString("type"));
                game.setDescription(rs.getString("description"));
                game.setPrice(rs.getInt("price"));
                game.setPublisherID(rs.getInt("publisher_id"));

                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString);
                game.setStatus(status);

                ArrayList<DLC> dlcList = getDLCs(game);
                game.setDLC(dlcList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public void getDLCDetails(DLC dlc) {
        conn.connect();
        String query = "SELECT * FROM item i WHERE item_id = " + dlc.getItemID();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                dlc.setItemID(rs.getInt("item_id"));
                dlc.setName(rs.getString("name"));
                dlc.setType(rs.getString("type"));
                dlc.setDescription(rs.getString("description"));
                dlc.setPrice(rs.getInt("price"));
                dlc.setPublisherID(rs.getInt("publisher_id"));
                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString);
                dlc.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public boolean updateGame(int gameID, String name, String price, String description) {
        conn.connect();
        String query = "UPDATE item"
                + " SET name='" + name + "',"
                + "price='" + price + "',"
                + "description='" + description
                + "' WHERE item_id = " + gameID;
        PreparedStatement stmt;
        try {
            stmt = conn.con.prepareStatement(query);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDLC(DLC dlc, String name, String price, String description) {
        conn.connect();
        String query = "UPDATE item"
                + " SET name='" + name + "',"
                + "price='" + price + "',"
                + "description='" + description
                + "WHERE item_id = " + dlc.getItemID();
        PreparedStatement stmt;
        try {
            stmt = conn.con.prepareStatement(query);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeDLC(DLC dlc) {
        conn.connect();
        String query = "UPDATE item"
                + " SET item_status='NOT_AVAILABLE'"
                + "WHERE item_id = " + dlc.getItemID();
        PreparedStatement stmt;
        try {
            stmt = conn.con.prepareStatement(query);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertIntoShoppingCart(User user, Item item) {
        ArrayList<ShoppingCart> cart = user.getCart();

        // cek atau memeriksa kalau cart ga kosong
        if (cart != null && !cart.isEmpty()) {
            //memeriksa atau cek item sudah ada di dalam cart. kalau sudah ada, maka return false.
            for (ShoppingCart shoppingCart : cart) {
                if (shoppingCart.getitemID() == item.getItemID()) {
                    return false;
                }
            }

            //mengambil ID transaksi baru dengan cara menghitung jumlah transaksi yang sudah ada di database dan menambahkannya dengan 1
            int transactionId = cart.get(0).getTransactionID();
            //membuat objek ShoppingCart baru dengan ID transaksi yang sama dan item yang ingin dimasukkan. lalu, menambahkan objek ShoppingCart baru ke dalam list cart dan return true.
            ShoppingCart temp = new ShoppingCart(transactionId, item.getItemID(), null);
            cart.add(temp);
            return true;
        } else {
            //kalau cart kosong, yang dilakukan selanjutnya adalah membuat transaction id baru
            int transactionId = getTransactions().size() + 1;
            ShoppingCart temp = new ShoppingCart(transactionId, item.getItemID(), null);

            //mastiin kalau cart user diinisialisasi jika sebelumnya tidak ada
            if (cart == null) {
                cart = new ArrayList<>();
                user.setCart(cart);
            }

            cart.add(temp);
            return true;
        }
    }

    public ArrayList<Transaction> getTransactions() {
        conn.connect();
        String query = "SELECT * FROM transaction";
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionID(rs.getInt("transaction_id"));
                transaction.setUserID(rs.getInt("user_id"));
                transaction.setDate(rs.getTimestamp("transaction_date"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); 
        }

        return transactions;
    }

    public boolean purchase(User user, ShoppingCart cart) {
        conn.connect();

        try {
            // Insert into transaction table
            String transactionQuery = "INSERT INTO transaction (user_id, transaction_date) VALUES (?, ?)";
            PreparedStatement transactionStmt = conn.con.prepareStatement(transactionQuery,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            transactionStmt.setInt(1, user.getId());
            transactionStmt.setTimestamp(2, Timestamp.from(Instant.now()));

            int rowsAffected = transactionStmt.executeUpdate();

            // Check if the insertion into transaction was successful
            if (rowsAffected > 0) {
                // Retrieve the generated transaction ID
                ResultSet generatedKeys = transactionStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int transactionId = generatedKeys.getInt(1);

                    // Insert into shoppingcart table
                    String shoppingCartQuery = "INSERT INTO shoppingcart (transaction_id, item_id, description) VALUES (?, ?, ?)";
                    PreparedStatement shoppingCartStmt = conn.con.prepareStatement(shoppingCartQuery);

                    shoppingCartStmt.setInt(1, transactionId);
                    shoppingCartStmt.setInt(2, cart.getitemID());
                    shoppingCartStmt.setString(3, "Item purchase"); 

                    // Execute the SQL statement for shoppingcart
                    shoppingCartStmt.executeUpdate();

                    String libraryQuery = "INSERT INTO library (user_id, item_id) VALUES (?, ?)";
                    PreparedStatement libraryQueryStmt = conn.con.prepareStatement(libraryQuery);

                    libraryQueryStmt.setInt(1, user.getId());
                    libraryQueryStmt.setInt(2, cart.getitemID());

                    // Execute the SQL statement for shoppingcart
                    libraryQueryStmt.executeUpdate();
                }
                user.getCart().clear();
                return true;

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }
        return false;
    }

    public ArrayList<ShoppingCart> getShoppingCart(int userID) {
        conn.connect();
        String query = "SELECT * FROM shoppingcart sc JOIN transaction t ON t.transaction_id = sc.transaction_id WHERE t.user_id = "
                + userID;
        ArrayList<ShoppingCart> transactions = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ShoppingCart transaction = new ShoppingCart();
                transaction.setTransactionID(rs.getInt("transaction_id"));
                transaction.setitemID(rs.getInt("item_id"));
                transaction.setDescription(rs.getString("description"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); 
        }

        return transactions;
    }

    public ArrayList<ShoppingCart> getShoppingCartByMonth(int month, int year) {
        conn.connect();
        String query = "SELECT * FROM shoppingcart sc JOIN transaction t ON t.transaction_id = sc.transaction_id WHERE MONTH(t.transaction_date) = "
                + month + " AND YEAR(t.transaction_date) = " + year;
        ArrayList<ShoppingCart> transactions = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ShoppingCart transaction = new ShoppingCart();
                transaction.setTransactionID(rs.getInt("transaction_id"));
                transaction.setitemID(rs.getInt("item_id"));
                transaction.setDescription(rs.getString("description"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); 
        }

        return transactions;
    }

    public ArrayList<Item> getItem() {
        conn.connect();
        String query = "SELECT * FROM item WHERE item_status = 'AVAILABLE'";
        ArrayList<Item> items = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setType(rs.getString("type"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getInt("price"));
                item.setPublisherID(rs.getInt("publisher_id"));

                // Handling the ItemStatus enum
                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString);
                item.setStatus(status);

                if (item instanceof Game) {
                    Game game = (Game) item;
                    ArrayList<Review> reviews = getReviewsForGame(game);
                    item.setReviews(reviews);
                } else if (item instanceof DLC) {
                    DLC dlc = (DLC) item;
                    ArrayList<Review> reviews = getReviewsForDLC(dlc);
                    item.setReviews(reviews);
                }
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); 
        }
        return items;
    }

    public ArrayList<Item> getAllItem() {
        conn.connect();
        String query = "SELECT * FROM item";
        ArrayList<Item> items = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setType(rs.getString("type"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getInt("price"));
                item.setPublisherID(rs.getInt("publisher_id"));

                // Handling the ItemStatus enum
                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString); // Assuming statusString is a valid enum name
                item.setStatus(status);

                // Handling reviews
                if (item instanceof Game) {
                    Game game = (Game) item;
                    ArrayList<Review> reviews = getReviewsForGame(game); // Implement getReviewsForGame method
                    item.setReviews(reviews);
                } else if (item instanceof DLC) {
                    DLC dlc = (DLC) item;
                    ArrayList<Review> reviews = getReviewsForDLC(dlc); // Implement getReviewsForGame method
                    item.setReviews(reviews);
                }
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); // Close the connection when done
        }
        return items;
    }

    public ArrayList<Item> getItemListRemove() {
        conn.connect();
        String query = "SELECT * FROM item WHERE item_status= 'AVAILABLE'";
        ArrayList<Item> items = new ArrayList<>();
        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setType(rs.getString("type"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getInt("price"));
                item.setPublisherID(rs.getInt("publisher_id"));

                // Handling the ItemStatus enum
                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString); // Assuming statusString is a valid enum name
                item.setStatus(status);

                // Handling reviews
                if (item instanceof Game) {
                    Game game = (Game) item;
                    ArrayList<Review> reviews = getReviewsForGame(game); // Implement getReviewsForGame method
                    item.setReviews(reviews);
                } else if (item instanceof DLC) {
                    DLC dlc = (DLC) item;
                    ArrayList<Review> reviews = getReviewsForDLC(dlc); // Implement getReviewsForGame method
                    item.setReviews(reviews);
                }
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); // Close the connection when done
        }

        return items;
    }

    public boolean updateStatusItem(int id) {
        conn.connect();
        String query = "UPDATE item SET item_status= 'NOT_AVAILABLE'"
                + "WHERE item_id='" + id + "'";
        try {
            Statement stmt = conn.con.createStatement();
            stmt.executeUpdate(query);
            return (true);
        } catch (SQLException e) {
            e.printStackTrace();
            return (false);
        }
    }

    public boolean updateStatusItem(int id, String s) {
        conn.connect();
        String query = "UPDATE item SET item_status= '" + s + "'"
                + "WHERE item_id='" + id + "'";
        try {
            Statement stmt = conn.con.createStatement();
            stmt.executeUpdate(query);
            return (true);
        } catch (SQLException e) {
            e.printStackTrace();
            return (false);
        }
    }

    public ArrayList<Item> getRemoveItem() {
        conn.connect();
        String query = "SELECT * FROM item WHERE item_status = 'NOT_AVAILABLE'";

        ArrayList<Item> items = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rsGame = stmt.executeQuery(query);
            while (rsGame.next()) {
                Item game = new Item();
                game.setItemID(rsGame.getInt("item_id"));
                game.setName(rsGame.getString("name"));
                game.setType(rsGame.getString("type"));
                game.setPrice(rsGame.getDouble("price"));
                game.setDescription(rsGame.getString("description"));
                game.setPublisherID(rsGame.getInt("publisher_id"));
                game.setStatus(ItemStatus.valueOf(rsGame.getString("item_status")));
                items.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public ArrayList<Item> getUserItem(User user) {
        conn.connect();
        String query = "SELECT * FROM library WHERE user_id = " + user.getId();
        ArrayList<Item> items = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setType(rs.getString("type"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getInt("price"));
                item.setPublisherID(rs.getInt("publisher_id"));

                // Handling the ItemStatus enum
                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString); // Assuming statusString is a valid enum name
                item.setStatus(status);

                // Handling reviews
                if (item instanceof Game) {
                    Game game = (Game) item;
                    ArrayList<Review> reviews = getReviewsForGame(game); // Implement getReviewsForGame method
                    item.setReviews(reviews);
                } else if (item instanceof DLC) {
                    DLC dlc = (DLC) item;
                    ArrayList<Review> reviews = getReviewsForDLC(dlc); // Implement getReviewsForGame method
                    item.setReviews(reviews);
                }
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); 
        }
        return items;
    }

    public ArrayList<Item> getPublisherItem(Publisher publisher) {
        conn.connect();
        String query = "SELECT * FROM item WHERE item_status = 'AVAILABLE' AND publisher_id = " + publisher.getId();
        ArrayList<Item> items = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setType(rs.getString("type"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getInt("price"));
                item.setPublisherID(rs.getInt("publisher_id"));

                // Handling the ItemStatus enum
                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString); // Assuming statusString is a valid enum name
                item.setStatus(status);

                //Handling reviews
                if (item instanceof Game) {
                    Game game = (Game) item;
                    ArrayList<Review> reviews = getReviewsForGame(game); // Implement getReviewsForGame method
                    item.setReviews(reviews);
                } else if (item instanceof DLC) {
                    DLC dlc = (DLC) item;
                    ArrayList<Review> reviews = getReviewsForDLC(dlc); // Implement getReviewsForGame method
                    item.setReviews(reviews);
                }
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); 
        }

        return items;
    }

    public Transaction getTransactionByID(int id) {
        conn.connect();
        String query = "SELECT * FROM transaction WHERE transaction_id = " + id;

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionID(rs.getInt("transaction_id"));
                transaction.setUserID(rs.getInt("user_id"));
                transaction.setDate(rs.getTimestamp("transaction_date"));
                return transaction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); 
        }

        return null;
    }

    public Item getItemById(int id) {
        conn.connect();
        String query = "SELECT * FROM item WHERE item_id = " + id;

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setType(rs.getString("type"));
                item.setPublisherID(rs.getInt("publisher_id"));
                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString);
                item.setStatus(status);
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        // Return null if the DLC is not found
        return null;
    }

    public boolean gift(User user, int target, ShoppingCart cart) {
        conn.connect();

        try {
            // Insert into transaction table
            String transactionQuery = "INSERT INTO transaction (user_id, transaction_date) VALUES (?, ?)";
            PreparedStatement transactionStmt = conn.con.prepareStatement(transactionQuery,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            transactionStmt.setInt(1, target);
            transactionStmt.setTimestamp(2, Timestamp.from(Instant.now()));

            int rowsAffected = transactionStmt.executeUpdate();

            // Check if the insertion into transaction was successful
            if (rowsAffected > 0) {
                // Retrieve the generated transaction ID
                ResultSet generatedKeys = transactionStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int transactionId = generatedKeys.getInt(1);

                    // Insert into shoppingcart table
                    String shoppingCartQuery = "INSERT INTO shoppingcart (transaction_id, item_id, description) VALUES (?, ?, ?)";
                    PreparedStatement shoppingCartStmt = conn.con.prepareStatement(shoppingCartQuery);

                    // Iterate through each item in the cart and add it to the shopping cart
                    shoppingCartStmt.setInt(1, transactionId);
                    shoppingCartStmt.setInt(2, cart.getitemID());
                    shoppingCartStmt.setString(3, "Gifted by " + user.getName());

                    // Execute the SQL statement for shopping cart
                    shoppingCartStmt.executeUpdate();

                    String libraryQuery = "INSERT INTO library (user_id, item_id) VALUES (?, ?)";
                    PreparedStatement libraryQueryStmt = conn.con.prepareStatement(libraryQuery);

                    libraryQueryStmt.setInt(1, target);
                    libraryQueryStmt.setInt(2, cart.getitemID());

                    // Execute the SQL statement for shoppingcart
                    libraryQueryStmt.executeUpdate();
                }
                user.getCart().clear();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            conn.disconnect();
        }
        return false;
    }

    public ArrayList<Item> getLibrary(User user) {
        conn.connect();
        String query = "SELECT * FROM item i JOIN library l ON l.item_id = i.item_id WHERE l.user_id = " + user.getId();
        ArrayList<Item> items = new ArrayList<>();

        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setType(rs.getString("type"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getInt("price"));
                item.setPublisherID(rs.getInt("publisher_id"));

                String statusString = rs.getString("item_status");
                ItemStatus status = ItemStatus.valueOf(statusString);
                item.setStatus(status);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect(); 
        }

        return items;
    }

    public double getWallet(int id) {
        conn.connect();
        String query = "SELECT wallet from users WHERE user_id = " + id;
        double wallet = 0;
        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                wallet = rs.getDouble("wallet");

                return wallet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wallet;
    }

}

