package bookstore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminPanel extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DatabaseManager dbManager;
    private DefaultListModel<Book> bookListModel;

    public AdminPanel() {
        dbManager = new DatabaseManager();
        bookListModel = new DefaultListModel<>();
        

        setTitle("Admin Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JList<Book> bookList = new JList<>(bookListModel);
        final JScrollPane scrollPane = new JScrollPane(bookList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2)); // Adjusted GridLayout to 5 rows, 2 columns

        JLabel titleLabel = new JLabel("Title:");
        final JTextField titleField = new JTextField();
        panel.add(titleLabel);
        panel.add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        final JTextField authorField = new JTextField();
        panel.add(authorLabel);
        panel.add(authorField);

        JLabel priceLabel = new JLabel("Price:");
        final JTextField priceField = new JTextField();
        panel.add(priceLabel);
        panel.add(priceField);

        JLabel stockLabel = new JLabel("Stock:");
        final JTextField stockField = new JTextField();
        panel.add(stockLabel);
        panel.add(stockField);

        JButton addButton = new JButton("Add Book");
        panel.add(addButton);

        JButton refreshButton = new JButton("Refresh List");
        panel.add(refreshButton);

        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = titleField.getText();
                    String author = authorField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int stock = Integer.parseInt(stockField.getText());

                    Book book = new Book(0, title, author, price, stock);
                    dbManager.addBook(book);
                    refreshBookList();

                    // Clear input fields after adding
                    titleField.setText("");
                    authorField.setText("");
                    priceField.setText("");
                    stockField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AdminPanel.this, "Please enter valid price and stock values.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshBookList();
            }
        });

        refreshBookList();
        setVisible(true);
    }

    private void refreshBookList() {
        List<Book> books = dbManager.getAllBooks();
        bookListModel.clear();
        for (Book book : books) {
            bookListModel.addElement(book);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdminPanel();
            }
        });
    }
}


