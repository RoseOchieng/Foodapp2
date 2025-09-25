package com.example.foodapp;

import android.os.Bundle;
import android.os.Handler; // For splash screen delay
import android.view.LayoutInflater; // For RecyclerView adapter's onCreateViewHolder
import android.view.View;
import android.view.ViewGroup; // For RecyclerView adapter's onCreateViewHolder - VERY COMMON MISSING IMPORT
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log; // Added for logging null object references

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager; // For RecyclerView layout manager
import androidx.recyclerview.widget.RecyclerView; // For RecyclerView itself

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private LinearLayout welcomeScreen;
    private LinearLayout signupScreen;
    private LinearLayout loginScreen; // New reference for login screen
    private LinearLayout categoriesScreen;
    private Button getStartedButton;
    private Button signUpButton;
    private TextView loginTextView; // "Already have an account? Log In"
    private Button loginButton; // New reference for login button
    private TextView createAccountTextView; // New reference for "Don't have an account? Create one"
    private RecyclerView categoriesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements by finding them by their IDs from the layout XML.
        // It's crucial to call findViewById AFTER setContentView().
        welcomeScreen = findViewById(R.id.welcome_screen);
        signupScreen = findViewById(R.id.signup_screen);
        loginScreen = findViewById(R.id.login_screen); // Correctly initialize login screen
        categoriesScreen = findViewById(R.id.categories_screen);
        getStartedButton = findViewById(R.id.get_started_button);
        signUpButton = findViewById(R.id.signup_button);
        loginTextView = findViewById(R.id.tv_login);
        loginButton = findViewById(R.id.login_button); // Initialize login button
        createAccountTextView = findViewById(R.id.tv_create_account); // Initialize create account TextView

        categoriesRecyclerView = findViewById(R.id.categories_recycler_view);

        // --- Initial Visibility Setup with Null Checks ---
        // Ensure all screens are properly initialized before attempting to set visibility.
        if (welcomeScreen != null) {
            welcomeScreen.setVisibility(View.VISIBLE);
        } else {
            Log.e("MainActivity", "welcome_screen LinearLayout not found!");
        }

        if (signupScreen != null) {
            signupScreen.setVisibility(View.GONE);
        } else {
            Log.e("MainActivity", "signup_screen LinearLayout not found!");
        }

        if (loginScreen != null) {
            loginScreen.setVisibility(View.GONE); // Ensure login screen is hidden
        } else {
            Log.e("MainActivity", "login_screen LinearLayout not found!");
        }

        if (categoriesScreen != null) {
            categoriesScreen.setVisibility(View.GONE);
        } else {
            Log.e("MainActivity", "categories_screen LinearLayout not found!");
        }

        // Simulate a splash screen delay, then transition to signup or login based on preference
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // For demonstration, let's go to the signup screen after splash
                // You could add logic here to check if a user is already logged in
                showSignUpScreen();
            }
        }, 3000); // 3 seconds delay

        // Set up click listeners only if the buttons/TextViews are found
        if (getStartedButton != null) {
            getStartedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSignUpScreen();
                }
            });
        } else {
            Log.e("MainActivity", "get_started_button not found!");
        }


        // --- Sign-up Screen Logic ---
        if (signUpButton != null) {
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText etUsername = findViewById(R.id.et_username);
                    EditText etEmail = findViewById(R.id.et_email);
                    EditText etPassword = findViewById(R.id.et_password);

                    String username = etUsername.getText().toString().trim();
                    String email = etEmail.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();

                    if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                        // After successful signup, you might go to login or directly to categories
                        showCategoriesScreen();
                    }
                }
            });
        } else {
            Log.e("MainActivity", "signup_button not found!");
        }


        // Click listener for "Already have an account? Log In" on Signup screen
        if (loginTextView != null) {
            loginTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoginScreen();
                }
            });
        } else {
            Log.e("MainActivity", "tv_login TextView not found!");
        }


        // --- Login Screen Logic ---
        if (loginButton != null) {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText etLoginEmail = findViewById(R.id.et_login_email);
                    EditText etLoginPassword = findViewById(R.id.et_login_password);

                    String email = etLoginEmail.getText().toString().trim();
                    String password = etLoginPassword.getText().toString().trim();

                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    } else {
                        // Basic validation for demonstration. In a real app, you'd check against a database.
                        if (email.equals("test@example.com") && password.equals("password")) {
                            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            showCategoriesScreen(); // Go to categories after successful login
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            Log.e("MainActivity", "login_button not found!");
        }


        // Click listener for "Don't have an account? Create one" on Login screen
        if (createAccountTextView != null) {
            createAccountTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSignUpScreen();
                }
            });
        } else {
            Log.e("MainActivity", "tv_create_account TextView not found!");
        }


        // --- Categories Screen Setup ---
        if (categoriesRecyclerView != null) {
            setupCategoriesRecyclerView();
        } else {
            Log.e("MainActivity", "categories_recycler_view not found!");
        }
    }

    // --- Helper methods to show/hide screens ---
    private void showWelcomeScreen() {
        if (welcomeScreen != null) welcomeScreen.setVisibility(View.VISIBLE);
        if (signupScreen != null) signupScreen.setVisibility(View.GONE);
        if (loginScreen != null) loginScreen.setVisibility(View.GONE);
        if (categoriesScreen != null) categoriesScreen.setVisibility(View.GONE);
    }

    private void showSignUpScreen() {
        if (welcomeScreen != null) welcomeScreen.setVisibility(View.GONE);
        if (signupScreen != null) signupScreen.setVisibility(View.VISIBLE);
        if (loginScreen != null) loginScreen.setVisibility(View.GONE); // Ensure login is hidden
        if (categoriesScreen != null) categoriesScreen.setVisibility(View.GONE);
    }

    private void showLoginScreen() {
        if (welcomeScreen != null) welcomeScreen.setVisibility(View.GONE);
        if (signupScreen != null) signupScreen.setVisibility(View.GONE);
        if (loginScreen != null) loginScreen.setVisibility(View.VISIBLE); // Show login screen
        if (categoriesScreen != null) categoriesScreen.setVisibility(View.GONE);
    }

    private void showCategoriesScreen() {
        if (welcomeScreen != null) welcomeScreen.setVisibility(View.GONE);
        if (signupScreen != null) signupScreen.setVisibility(View.GONE);
        if (loginScreen != null) loginScreen.setVisibility(View.GONE);
        if (categoriesScreen != null) categoriesScreen.setVisibility(View.VISIBLE);
    }

    private void setupCategoriesRecyclerView() {
        // Sample data for categories (as before)
        List<Category> categories = new ArrayList<>();
        // Ensure you have these drawables (e.g., pizza.png, burger.png in res/drawable)
        categories.add(new Category("Pizza", R.drawable.pizza));
        categories.add(new Category("Burgers", R.drawable.burger));
        categories.add(new Category("Pasta", R.drawable.pasta));
        categories.add(new Category("Salads", R.drawable.salad));
        categories.add(new Category("Desserts", R.drawable.dessert));
        categories.add(new Category("Drinks", R.drawable.drink));

        // Set up the RecyclerView
        CategoryAdapter adapter = new CategoryAdapter(categories);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns
        categoriesRecyclerView.setAdapter(adapter);
    }

    // --- Helper Classes for Categories (as before) ---

    // Model class for a Category
    public static class Category {
        private String name;
        private int imageResId;

        public Category(String name, int imageResId) {
            this.name = name;
            this.imageResId = imageResId;
        }

        public String getName() {
            return name;
        }

        public int getImageResId() {
            return imageResId;
        }
    }

    // RecyclerView Adapter for Categories
    public static class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

        private List<Category> categoryList;

        public CategoryAdapter(List<Category> categoryList) {
            this.categoryList = categoryList;
        }

        @Override
        public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
            return new CategoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CategoryViewHolder holder, int position) {
            Category category = categoryList.get(position);
            holder.categoryName.setText(category.getName());
            holder.categoryImage.setImageResource(category.getImageResId());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked: " + category.getName(), Toast.LENGTH_SHORT).show();
                    // In a real app, you would navigate to a detailed list of food items for this category
                }
            });
        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }

        public static class CategoryViewHolder extends RecyclerView.ViewHolder {
            ImageView categoryImage;
            TextView categoryName;

            public CategoryViewHolder(View itemView) {
                super(itemView);
                categoryImage = itemView.findViewById(R.id.category_image);
                categoryName = itemView.findViewById(R.id.category_name);
            }
        }
    }
}
