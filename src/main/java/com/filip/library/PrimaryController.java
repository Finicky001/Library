package com.filip.library;

import com.filip.beans.*;
import com.filip.tools.Password;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class PrimaryController implements Initializable {

    String surl = "http://localhost:8080/api";

    ObservableList<User> users = FXCollections.observableArrayList();
    ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private Button btnSubmit;
    @FXML
    private ImageView iwBooks;
    @FXML
    private ImageView iwLogout;
    @FXML
    private ImageView iwUsers;
    @FXML
    private ImageView iwCounter;
    @FXML
    private ImageView iwTransaction;
    @FXML
    private ImageView iwPrint;
    @FXML
    private ImageView iwStats;
    @FXML
    private ImageView iwSearch;
    @FXML
    private ImageView iwSettings;
    @FXML
    private AnchorPane anchorUsers;
    @FXML
    private AnchorPane anchorBooks;
    @FXML
    private AnchorPane anchorCounter;
    @FXML
    private AnchorPane anchorTransactions;
    @FXML
    private AnchorPane anchorPrint;
    @FXML
    private AnchorPane anchorStats;
    @FXML
    private AnchorPane anchorSearch;
    @FXML
    private AnchorPane anchorSettings;
    @FXML
    private AnchorPane anchorMenu;
    @FXML
    private ImageView iwBackUsers;
    @FXML
    private AnchorPane anchorLogin;
    @FXML
    private Label lblLogin;
    @FXML
    private Label txtHello;

    @FXML
    private TextField txtIme;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtBroj;
    @FXML
    private TextField txtPrezime;
    @FXML
    private TextField txtAdresa;
    @FXML
    private ComboBox<Zupanija> cbZupanija;
    @FXML
    private ComboBox<Grad> cbGrad;
    @FXML
    private ComboBox<Role> cbRole;
    @FXML
    private Button btnAddUser;
    @FXML
    private TextField txtBookName;
    @FXML
    private TextField txtReleaseYear;
    @FXML
    private TextField txtNumberOfPages;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtISBN;
    @FXML
    private TextField txtNumberOfCopies;
    @FXML
    private Button btnAddBook;
    @FXML
    private AnchorPane anchorUserSearch;
    @FXML
    private TextField txtUserSearch;
    @FXML
    private Button btnUserSearch;
    @FXML
    private TableView<User> twUsers;
    @FXML
    private AnchorPane anchorBookSearch;
    @FXML
    private TableView<Book> twBooks;
    @FXML
    private TextField txtBookSearch;
    @FXML
    private Button btnBookSearch;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        anchorLogin.setVisible(true);
        anchorMenu.setVisible(false);
        anchorUsers.setVisible(false);
        anchorBooks.setVisible(false);
        anchorCounter.setVisible(false);
        anchorTransactions.setVisible(false);
        anchorPrint.setVisible(false);
        anchorStats.setVisible(false);
        anchorSearch.setVisible(false);
        anchorSettings.setVisible(false);
        anchorUserSearch.setVisible(false);
        anchorBookSearch.setVisible(false);
    }

    @FXML
    private void logoutClicked(MouseEvent event) {
        anchorLogin.setVisible(true);
        anchorMenu.setVisible(false);
        lblLogin.setText("");
    }

    @FXML
    private void submitClicked(MouseEvent event) throws Exception {
        User user = getUserByUsername(txtUsername.getText());

        if (user.getUsername() == null) {
            lblLogin.setText("Wrong username!");
            return;
        }

        boolean isPasswordCorrect = Password.checkPassword(txtPassword.getText(), user.getPassword());

        if (isPasswordCorrect) {
            anchorMenu.setVisible(true);
            anchorLogin.setVisible(false);
            txtHello.setText("Hello, " + txtUsername.getText() + "!");
        } else {
            lblLogin.setText("Wrong Password!");
        }

    }

    private String executeApi(String path, String method) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            System.out.println("Can't fetch the page...");
            System.exit(1);
        }

        String result = "";
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            result += scanner.nextLine();
        }
        scanner.close();
        return result;
    }

    private String executeApi2(String surl, String query) throws Exception {
        String url = surl;
        String data = query;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Java client");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(data);
        wr.flush();
        wr.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        return response.toString();
    }

    @FXML
    private void usersClicked(MouseEvent event) throws Exception {
        anchorMenu.setVisible(false);
        anchorUsers.setVisible(true);

        cbZupanija.getItems().addAll(getZupanije());
        cbZupanija.getSelectionModel().selectFirst();
        cbZupanija.setOnAction(t -> {
            cbGrad.getItems().clear();
            try {
                cbGrad.setItems(getGradoviByZupanija(cbZupanija.getSelectionModel().getSelectedItem().getId() + ""));
                cbGrad.getSelectionModel().selectFirst();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        cbRole.getItems().addAll(getRoles());
        cbRole.getSelectionModel().selectFirst();
    }

    @FXML
    private void booksClicked(MouseEvent event) {
        anchorMenu.setVisible(false);
        anchorBooks.setVisible(true);
    }

    @FXML
    private void counterClicked(MouseEvent event) {
        anchorMenu.setVisible(false);
        anchorCounter.setVisible(true);
    }

    @FXML
    private void transactionClicked(MouseEvent event) {
        anchorMenu.setVisible(false);
        anchorTransactions.setVisible(true);
    }

    @FXML
    private void printClicked(MouseEvent event) {
        anchorMenu.setVisible(false);
        anchorPrint.setVisible(true);
    }

    @FXML
    private void statsClicked(MouseEvent event) {
        anchorMenu.setVisible(false);
        anchorStats.setVisible(true);
    }

    @FXML
    private void searchClicked(MouseEvent event) {
        anchorMenu.setVisible(false);
        anchorSearch.setVisible(true);
    }

    @FXML
    private void settingsClicked(MouseEvent event) {
        anchorMenu.setVisible(false);
        anchorSettings.setVisible(true);
    }

    @FXML
    private void backClicked(MouseEvent event) {
        anchorMenu.setVisible(true);
        anchorUsers.setVisible(false);
        anchorBooks.setVisible(false);
        anchorCounter.setVisible(false);
        anchorTransactions.setVisible(false);
        anchorPrint.setVisible(false);
        anchorStats.setVisible(false);
        anchorSearch.setVisible(false);
        anchorSettings.setVisible(false);
    }

    private User getUserByUsername(String username) throws Exception {

        String path = surl + "/getemployeebyusername?username=" + username;

        Gson gson = new Gson();
        String json = executeApi(path, "GET");
        User person = gson.fromJson(json, User.class);

        return person;
    }

    private void fillUsers() throws Exception {
        String path = surl + "/getusers";

        Gson gson = new Gson();
        String json = executeApi(path, "GET");
        JsonArray array = gson.fromJson(json, JsonArray.class);
        for (JsonElement element : array) {
            User person = gson.fromJson(element, User.class);

            users.add(person);
        }
    }

    private void makeUserTable() throws Exception {
        ObservableList<Grad> gradovi = getGradovi();

        String path = surl + "/getusers";

        TableColumn<User, Integer> id = new TableColumn<>("ID");

        TableColumn<User, String> city = new TableColumn<>("Grad");
        TableColumn<User, String> firstName = getTableColumn("Ime", "firstName");
        TableColumn<User, String> lastName = getTableColumn("Prezime", "lastName");
        TableColumn<User, String> email = getTableColumn("Email", "email");
        TableColumn<User, String> username = getTableColumn("Username", "username");
        TableColumn<User, String> phoneNumber = getTableColumn("Mobitel", "phoneNumber");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        city.setCellValueFactory(new PropertyValueFactory<>("grad"));

        String[] cityNames = new String[gradovi.size()];
        for (int i = 0; i < gradovi.size(); i++) {
            cityNames[i] = gradovi.get(i).getNazivgrada();
        }

        city.setCellFactory(ComboBoxTableCell.forTableColumn(cityNames));

        firstName.setMinWidth(100);
        lastName.setMinWidth(100);
        email.setMinWidth(200);
        username.setMinWidth(200);
        phoneNumber.setMinWidth(200);

        twUsers.setItems(users);
        twUsers.getColumns().addAll(id, firstName, lastName, email, phoneNumber, username, city);
    }

    private TableColumn<User, String> getTableColumn(String columnName, String field) {
        TableColumn<User, String> temp = new TableColumn<>(columnName);
        temp.setCellValueFactory(new PropertyValueFactory<>(field));
        temp.setCellFactory(TextFieldTableCell.forTableColumn());
        return temp;
    }

    private ObservableList<Grad> getGradovi() throws Exception {
        ObservableList<Grad> gradovi = FXCollections.observableArrayList();

        String path = surl + "/getcities";

        Gson gson = new Gson();
        String json = executeApi(path, "GET");
        JsonArray array = gson.fromJson(json, JsonArray.class);
        for (JsonElement element : array) {
            Grad city = gson.fromJson(element, Grad.class);
            gradovi.add(city);
        }
        return gradovi;
    }

    @FXML
    private void btnAddUserClicked(MouseEvent event) throws Exception {
        String path = "firstName=" + txtIme.getText() + "&lastName=" + txtPrezime.getText() + "&email=" + txtEmail.getText() + "&phoneNumber=" + txtBroj.getText() + "&adress=" + txtAdresa.getText() + "&zupanija=" + cbZupanija.getSelectionModel().getSelectedItem().getId() + "&grad=" + cbGrad.getSelectionModel().getSelectedItem().getId() + "&role=" + cbRole.getSelectionModel().getSelectedItem().getId();
//                             addauser?firstName=    Mario               &lastName=    Muller                  &email=    mmuller@gmail.com     &phoneNumber=    123456789            &adress=    Ruzina74               &zupanija=    Istarska                                            &grad=    Pula                                            &role=    Member
        path = path.replaceAll(" ", "%20");
//        System.out.println(path);
        Gson gson = new Gson();
        String json = executeApi2(surl + "/addauser2?", path);
        gson.fromJson(json, User.class);
    }

    private ObservableList<Zupanija> getZupanije() throws Exception {
        ObservableList<Zupanija> zupanije = FXCollections.observableArrayList();

        String path = surl + "/getzupanije";

        Gson gson = new Gson();
        String json = executeApi(path, "GET");
        JsonArray array = gson.fromJson(json, JsonArray.class);
        for (JsonElement element : array) {
            Zupanija zupanija = gson.fromJson(element, Zupanija.class);
            zupanije.add(zupanija);
        }
        return zupanije;
    }

    private ObservableList<Grad> getGradoviByZupanija(String id) throws Exception {
        ObservableList<Grad> gradovi = FXCollections.observableArrayList();

        String path = surl + "/getcitiesbyzupanija?zupanijaid=" + id;

        Gson gson = new Gson();
        String json = executeApi(path, "GET");
        JsonArray array = gson.fromJson(json, JsonArray.class);
        for (JsonElement element : array) {
            Grad grad = gson.fromJson(element, Grad.class);
            gradovi.add(grad);
        }

        return gradovi;
    }

    private ObservableList<Role> getRoles() throws Exception {
        ObservableList<Role> roles = FXCollections.observableArrayList();

        String path = surl + "/getroles";

        Gson gson = new Gson();
        String json = executeApi(path, "GET");
        JsonArray array = gson.fromJson(json, JsonArray.class);
        for (JsonElement element : array) {
            Role role = gson.fromJson(element, Role.class);
            roles.add(role);
        }

        return roles;
    }

    @FXML
    private void btnAddBookClicked(MouseEvent event) throws Exception {

        String path = surl + "bookName=" + txtBookName.getText() + "&author=" + txtAuthor.getText() + "&releaseYear=" + txtReleaseYear.getText() + "&pageNumber=" + txtNumberOfPages.getText() + "&isbn=" + txtISBN.getText() + "&copyNumber=" + txtNumberOfCopies.getText();
        path = path.replaceAll(" ", "%20");
        path = path.replaceAll("'", "");
        Gson gson = new Gson();
        String json = executeApi2(surl + "/addnewbook?", path);
        gson.fromJson(json, User.class);
    }

    @FXML
    private void UserSearchClicked(MouseEvent event) throws Exception {
        anchorLogin.setVisible(false);
        anchorMenu.setVisible(false);
        anchorUsers.setVisible(false);
        anchorBooks.setVisible(false);
        anchorCounter.setVisible(false);
        anchorTransactions.setVisible(false);
        anchorPrint.setVisible(false);
        anchorStats.setVisible(false);
        anchorSearch.setVisible(false);
        anchorSettings.setVisible(false);
        anchorUserSearch.setVisible(true);

        twUsers.getItems().clear();
        twUsers.getColumns().clear();
        fillUsers();
        makeUserTable();
    }

    @FXML
    private void BookSearchClicked(MouseEvent event) throws Exception {
        anchorLogin.setVisible(false);
        anchorMenu.setVisible(false);
        anchorUsers.setVisible(false);
        anchorBooks.setVisible(false);
        anchorCounter.setVisible(false);
        anchorTransactions.setVisible(false);
        anchorPrint.setVisible(false);
        anchorStats.setVisible(false);
        anchorSearch.setVisible(false);
        anchorSettings.setVisible(false);
        anchorUserSearch.setVisible(false);
        anchorBookSearch.setVisible(true);

        twBooks.getItems().clear();
        twBooks.getColumns().clear();
        fillBooks();
        makeBookTable();
    }

    @FXML
    private void btnUserSearchClicked(ActionEvent event) {

        FilteredList<User> filteredUsers = new FilteredList<>(users, b -> true);
        filteredUsers.setPredicate(searchModel -> {

            if (txtUserSearch.getText().isEmpty() || txtUserSearch.getText().isBlank() || txtUserSearch.getText() == null) {
                return true;
            }

            if (searchModel.getFirstName().toLowerCase().contains(txtUserSearch.getText().toLowerCase())) {
                return true;
            } else if (searchModel.getLastName().toLowerCase().contains(txtUserSearch.getText().toLowerCase())) {
                return true;
            } else if (searchModel.getEmail().toLowerCase().contains(txtUserSearch.getText().toLowerCase())) {
                return true;
            } else if (searchModel.getGrad().getNazivgrada().toLowerCase().contains(txtUserSearch.getText().toLowerCase())) {
                return true;
            } else if (searchModel.getPhoneNumber().toLowerCase().contains(txtUserSearch.getText().toLowerCase())) {
                return true;
            } else {
                return false;
            }

        });
        twUsers.setItems(filteredUsers);
    }

    private void fillBooks() throws Exception {
        String path = surl + "/getbooks";

        Gson gson = new Gson();
        String json = executeApi(path, "GET");
        JsonArray array = gson.fromJson(json, JsonArray.class);
        for (JsonElement element : array) {
            Book book = gson.fromJson(element, Book.class);

            books.add(book);
        }
    }

    private void makeBookTable() throws Exception {
        ObservableList<Status> statuses = getStatusi();

        String path = surl + "/getbooks";

        TableColumn<Book, Integer> id = new TableColumn<>("ID");

        TableColumn<Book, String> bookName = getTableColumn2("Ime knjige", "bookName");
        TableColumn<Book, String> author = getTableColumn2("Ime autora", "author");
        TableColumn<Book, String> releaseYear = getTableColumn2("Godina", "releaseYear");
        TableColumn<Book, String> isbn = getTableColumn2("ISBN", "isbn");
//        TableColumn<Book, String> status = new TableColumn<>("Status");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
//        status.setCellValueFactory(new PropertyValueFactory<>("status"));
//
//        String[] statusi = new String[statuses.size()];
//        for (int i = 0; i < statuses.size(); i++) {
//            statusi[i] = statuses.get(i).getStatus();
//        }

//        status.setCellFactory(ComboBoxTableCell.forTableColumn(statusi));

        bookName.setMinWidth(200);
        author.setMinWidth(150);
        releaseYear.setMinWidth(50);
        isbn.setMinWidth(200);

        twBooks.setItems(books);
        twBooks.getColumns().addAll(id, bookName, author, releaseYear, isbn);
    }

    private TableColumn<Book, String> getTableColumn2(String columnName, String field) {
        TableColumn<Book, String> temp = new TableColumn<>(columnName);
        temp.setCellValueFactory(new PropertyValueFactory<>(field));
        temp.setCellFactory(TextFieldTableCell.forTableColumn());
        return temp;
    }

    @FXML
    private void btnBookSearchClicked(ActionEvent event) {

        FilteredList<Book> filteredBooks = new FilteredList<>(books, b -> true);
        filteredBooks.setPredicate(searchModel -> {

            if (txtBookSearch.getText().isEmpty() || txtBookSearch.getText().isBlank() || txtBookSearch.getText() == null) {
                return true;
            }

            if (searchModel.getBookName().toLowerCase().contains(txtBookSearch.getText().toLowerCase())) {
                return true;
            } else if (searchModel.getAuthor().toLowerCase().contains(txtBookSearch.getText().toLowerCase())) {
                return true;
            } else if (searchModel.getReleaseYear().toLowerCase().contains(txtBookSearch.getText().toLowerCase())) {
                return true;
            } else if (searchModel.getIsbn().toLowerCase().contains(txtBookSearch.getText().toLowerCase())) {
                return true;
            } else {
                return false;
            }

        });
        twBooks.setItems(filteredBooks);
    }

    private ObservableList<Status> getStatusi() throws Exception {
        ObservableList<Status> statuses = FXCollections.observableArrayList();

        String path = surl + "/getstatuses";

        Gson gson = new Gson();
        String json = executeApi(path, "GET");
        JsonArray array = gson.fromJson(json, JsonArray.class);
        for (JsonElement element : array) {
            Status status = gson.fromJson(element, Status.class);
            statuses.add(status);
        }
        return statuses;
    }
}
