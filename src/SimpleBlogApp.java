/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class SimpleBlogApp extends JFrame {

    private JTextField txtTitle;
    private JTextArea txtContent;
    private JTextArea txtPostDetails;
    private JList<String> postList;
    private DefaultListModel<String> listModel;
    private JLabel lblStatus;

    private ArrayList<BlogPost> posts = new ArrayList<>();

    private final File storageFile =
            new File("blog_posts.dat");

    // Color theme
    private final Color PURPLE = new Color(91, 63, 180);
    private final Color LIGHT_BG = new Color(240, 244, 255);
    private final Color GREEN = new Color(22, 163, 74);
    private final Color ORANGE = new Color(234, 138, 30);
    private final Color RED = new Color(220, 53, 69);
    private final Color DARK = new Color(45, 45, 75);
    private final Color WHITE = Color.WHITE;

    // Blog post class
    private static class BlogPost implements Serializable {
        private static final long serialVersionUID = 1L;

        String title;
        String content;

        BlogPost(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }

    public SimpleBlogApp() {
        setTitle("My Simple Blog App");
        setSize(1000, 680);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadPosts();
        refreshPostList();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(LIGHT_BG);
        mainPanel.setBorder(new EmptyBorder(18, 18, 18, 18));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PURPLE);
        header.setBorder(new EmptyBorder(18, 15, 18, 15));

        JLabel heading = new JLabel(
                "MY SIMPLE BLOG", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 30));
        heading.setForeground(WHITE);

        JLabel subtitle = new JLabel(
                "Create  |  Read  |  Manage Your Posts",
                SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(235, 228, 255));

        JPanel headingPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headingPanel.setOpaque(false);
        headingPanel.add(heading);
        headingPanel.add(subtitle);

        header.add(headingPanel, BorderLayout.CENTER);
        mainPanel.add(header, BorderLayout.NORTH);

        // Left side: create and list posts
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 215, 240), 1),
                new EmptyBorder(12, 12, 12, 12)));

        // Input form
        JPanel formPanel = new JPanel(new BorderLayout(8, 8));
        formPanel.setBackground(WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(
                        new Color(210, 200, 240)),
                "Create New Post"));

        JLabel lblTitle = new JLabel("Post Title");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTitle.setForeground(DARK);

        txtTitle = new JTextField();
        txtTitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtTitle.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(210, 205, 235)),
                new EmptyBorder(8, 8, 8, 8)));

        JPanel titlePanel = new JPanel(new BorderLayout(5, 5));
        titlePanel.setBackground(WHITE);
        titlePanel.add(lblTitle, BorderLayout.NORTH);
        titlePanel.add(txtTitle, BorderLayout.CENTER);

        JLabel lblContent = new JLabel("Post Content");
        lblContent.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblContent.setForeground(DARK);

        txtContent = new JTextArea(6, 25);
        txtContent.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtContent.setLineWrap(true);
        txtContent.setWrapStyleWord(true);
        txtContent.setBackground(new Color(250, 249, 255));
        txtContent.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane contentScroll = new JScrollPane(txtContent);
        contentScroll.setBorder(BorderFactory.createLineBorder(
                new Color(210, 205, 235)));

        JPanel contentPanel = new JPanel(new BorderLayout(5, 5));
        contentPanel.setBackground(WHITE);
        contentPanel.add(lblContent, BorderLayout.NORTH);
        contentPanel.add(contentScroll, BorderLayout.CENTER);

        JButton btnAdd = new JButton(" + Add Post ");
        JButton btnClear = new JButton(" Clear Fields ");

        styleButton(btnAdd, GREEN);
        styleButton(btnClear, ORANGE);

        JPanel formButtons = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 5));
        formButtons.setBackground(WHITE);
        formButtons.add(btnAdd);
        formButtons.add(btnClear);

        JPanel formCenter = new JPanel(new BorderLayout(10, 10));
        formCenter.setBackground(WHITE);
        formCenter.add(titlePanel, BorderLayout.NORTH);
        formCenter.add(contentPanel, BorderLayout.CENTER);

        formPanel.add(formCenter, BorderLayout.CENTER);
        formPanel.add(formButtons, BorderLayout.SOUTH);

        // Saved posts list
        listModel = new DefaultListModel<>();
        postList = new JList<>(listModel);
        postList.setFont(new Font("SansSerif", Font.PLAIN, 14));
        postList.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        postList.setBackground(new Color(248, 246, 255));
        postList.setSelectionBackground(
                new Color(126, 87, 194));
        postList.setSelectionForeground(WHITE);
        postList.setFixedCellHeight(32);

        JScrollPane listScroll = new JScrollPane(postList);
        listScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(
                        new Color(210, 200, 240)),
                "Your Saved Posts"));

        JButton btnDelete = new JButton(" Delete Selected Post ");
        styleButton(btnDelete, RED);

        JPanel deletePanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 5, 5));
        deletePanel.setBackground(WHITE);
        deletePanel.add(btnDelete);

        JPanel savedPanel = new JPanel(new BorderLayout(8, 8));
        savedPanel.setBackground(WHITE);
        savedPanel.add(listScroll, BorderLayout.CENTER);
        savedPanel.add(deletePanel, BorderLayout.SOUTH);

        JSplitPane leftSplit = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT, formPanel, savedPanel);
        leftSplit.setResizeWeight(0.55);
        leftSplit.setDividerSize(7);
        leftSplit.setBorder(null);

        leftPanel.add(leftSplit, BorderLayout.CENTER);

        // Right side: read posts
        JPanel rightPanel = new JPanel(new BorderLayout(8, 8));
        rightPanel.setBackground(WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 215, 240), 1),
                new EmptyBorder(12, 12, 12, 12)));

        JLabel readHeading = new JLabel(
                "Read Your Blog Post");
        readHeading.setFont(new Font("SansSerif", Font.BOLD, 19));
        readHeading.setForeground(PURPLE);
        readHeading.setBorder(new EmptyBorder(5, 5, 10, 5));

        txtPostDetails = new JTextArea();
        txtPostDetails.setFont(
                new Font("SansSerif", Font.PLAIN, 16));
        txtPostDetails.setEditable(false);
        txtPostDetails.setLineWrap(true);
        txtPostDetails.setWrapStyleWord(true);
        txtPostDetails.setBackground(new Color(250, 249, 255));
        txtPostDetails.setForeground(DARK);
        txtPostDetails.setMargin(new Insets(15, 15, 15, 15));
        txtPostDetails.setText(
                "Welcome to My Simple Blog!\n\n"
                + "Select a saved post from the left panel "
                + "to read its content here.");

        JScrollPane detailsScroll = new JScrollPane(txtPostDetails);
        detailsScroll.setBorder(BorderFactory.createLineBorder(
                new Color(225, 220, 240)));

        rightPanel.add(readHeading, BorderLayout.NORTH);
        rightPanel.add(detailsScroll, BorderLayout.CENTER);

        JSplitPane mainSplit = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        mainSplit.setResizeWeight(0.48);
        mainSplit.setDividerSize(8);
        mainSplit.setBorder(null);

        mainPanel.add(mainSplit, BorderLayout.CENTER);

        // Footer
        lblStatus = new JLabel("Ready to write your first blog post!");
        lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblStatus.setForeground(PURPLE);
        lblStatus.setBorder(new EmptyBorder(5, 3, 0, 3));

        mainPanel.add(lblStatus, BorderLayout.SOUTH);

        add(mainPanel);

        // Button actions
        btnAdd.addActionListener(e -> addPost());

        btnClear.addActionListener(e -> {
            txtTitle.setText("");
            txtContent.setText("");
            txtTitle.requestFocusInWindow();
            lblStatus.setText("Input fields cleared.");
        });

        postList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedPost();
            }
        });

        btnDelete.addActionListener(e -> deletePost());
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 14, 10, 14));
    }

    // Create and save a post
    private void addPost() {
        String title = txtTitle.getText().trim();
        String content = txtContent.getText().trim();

        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both the post title and content.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        BlogPost post = new BlogPost(title, content);
        posts.add(post);

        if (savePosts()) {
            refreshPostList();
            postList.setSelectedIndex(posts.size() - 1);

            txtTitle.setText("");
            txtContent.setText("");

            lblStatus.setText("Post added and saved successfully!");

            JOptionPane.showMessageDialog(
                    this,
                    "Your blog post has been saved successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            posts.remove(posts.size() - 1);

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save the post. Please check file access.",
                    "Storage Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Read selected post
    private void showSelectedPost() {
        int index = postList.getSelectedIndex();

        if (index >= 0 && index < posts.size()) {
            BlogPost post = posts.get(index);

            txtPostDetails.setText(
                    post.title + "\n"
                    + "----------------------------------------"
                    + "\n\n"
                    + post.content);

            txtPostDetails.setCaretPosition(0);
            lblStatus.setText("Currently reading: " + post.title);
        }
    }

    // Delete selected post
    private void deletePost() {
        int index = postList.getSelectedIndex();

        if (index == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a post from the list first.",
                    "No Post Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
                this,
                "Do you really want to delete this post?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (answer == JOptionPane.YES_OPTION) {
            BlogPost removed = posts.remove(index);

            if (savePosts()) {
                refreshPostList();
                txtPostDetails.setText(
                        "Select another post to read its content.");
                lblStatus.setText("Post deleted successfully!");
            } else {
                posts.add(index, removed);
                refreshPostList();
                postList.setSelectedIndex(index);

                JOptionPane.showMessageDialog(
                        this,
                        "Could not update the local storage file.",
                        "Storage Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Refresh list of saved posts
    private void refreshPostList() {
        listModel.clear();

        for (BlogPost post : posts) {
            listModel.addElement(post.title);
        }
    }

    // Save posts to local file
    private boolean savePosts() {
        try (ObjectOutputStream output =
                     new ObjectOutputStream(
                             new FileOutputStream(storageFile))) {

            output.writeObject(posts);
            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Load posts when the application starts
    private void loadPosts() {
        if (!storageFile.exists()) {
            return;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(storageFile))) {

            Object data = input.readObject();

            if (!(data instanceof ArrayList<?>)) {
                throw new IOException("Invalid blog storage format.");
            }

            ArrayList<?> savedItems = (ArrayList<?>) data;
            ArrayList<BlogPost> loadedPosts = new ArrayList<>();

            for (Object item : savedItems) {
                if (!(item instanceof BlogPost)) {
                    throw new IOException("Invalid saved post.");
                }

                loadedPosts.add((BlogPost) item);
            }

            posts = loadedPosts;

        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Saved posts could not be loaded.\n"
                    + ex.getMessage(),
                    "Storage Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimpleBlogApp().setVisible(true);
        });
    }
}
