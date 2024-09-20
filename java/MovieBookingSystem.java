import java.util.Scanner;
import admin.Admin;
import user.User;

abstract class Movie {
    protected String title;
    protected String genre;
    protected int duration;
    protected double rating;
    protected int totalSeats;
    protected boolean[] seats;
    protected String date;
    protected String time;

    public Movie(String title, String genre, int duration, double rating, 
                 int totalSeats, String date, String time) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
        this.totalSeats = totalSeats;
        this.date = date;
        this.time = time;
        this.seats = new boolean[totalSeats]; 
        for (int i = 0; i < totalSeats; i++) {
            seats[i] = false; 
        }
    }

    public abstract void displayDetails();
    public boolean bookSeat(int seatNo) {
        if (seatNo > 0 && seatNo <= totalSeats) {
            if (!seats[seatNo - 1]) {
                seats[seatNo - 1] = true; 
                System.out.println("Seat " + seatNo + " booked successfully!");
                return true;
            } else {
                System.out.println("Sorry, seat " + seatNo + " is already booked!");
                return false;
            }
        } else {
            System.out.println("Invalid seat number!");
            return false;
        }
    }

    public boolean cancelBooking(int seatNo) {
        if (seatNo > 0 && seatNo <= totalSeats) {
            if (seats[seatNo - 1]) {
                seats[seatNo - 1] = false; 
                System.out.println("Booking for seat " + seatNo + " canceled successfully!");
                return true;
            } else {
                System.out.println("Sorry, seat " + seatNo + " is not booked!");
                return false;
            }
        } else {
            System.out.println("Invalid seat number!");
            return false;
        }
    }

    public void displayAvailableSeats() {
        System.out.println("Available Seats:");
        for (int i = 0; i < totalSeats; i++) {
            if (!seats[i]) {
                System.out.print((i + 1) + " ");
            }
        }
        System.out.println();
    }
}

class RegularMovie extends Movie {
    private double ticketPrice;

    public RegularMovie(String title, String genre, int duration, double rating, double ticketPrice, int totalSeats, String date, String time) {
        super(title, genre, duration, rating, totalSeats, date, time);
        this.ticketPrice = ticketPrice;
    }

    @Override
    public void displayDetails() {
        System.out.println("Title: " + title);
        System.out.println("Genre: " + genre);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Rating: " + rating);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Ticket Price: Rs." + ticketPrice);
        displayAvailableSeats();
    }
}

interface Booking {
    void bookTicket();
    void cancelTicket();
    void displayMovieDetails();
    void addNewMovie();
}

public class MovieBookingSystem implements Booking {
    private Movie[] movies;
    private int movieCount;
    private Scanner scanner;
    private User user;
    private Admin admin;

    public MovieBookingSystem() {
        movies = new Movie[10];
        movieCount = 0;
        scanner = new Scanner(System.in);
        user = new User("user", "password");
        admin = new Admin("admin", "password");
    }

    public void addMovie(Movie movie) {
        if (movieCount < 10) {
            movies[movieCount++] = movie;
        } else {
            System.out.println("Maximum limit reached for movies!");
        }
    }

    public void displayMovies() {
        System.out.println("Available Movies:");
        for (int i = 0; i < movieCount; i++) {
            System.out.println((i + 1) + ". " + movies[i].title);
        }
    }

    @Override
    public void bookTicket() {
        displayMovies();
        System.out.print("Enter the movie number: ");
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= movieCount) {
            movies[choice - 1].displayDetails();
            System.out.print("Enter the seat number to book: ");
            int seatNo = scanner.nextInt();
            if (movies[choice - 1].bookSeat(seatNo)) {
                System.out.println("Booking successful!");
            } else {
                System.out.println("Booking failed. Please try again!");
            }
        } else {
            System.out.println("Invalid movie number!");
        }
    }

    @Override
    public void cancelTicket() {
        displayMovies();
        System.out.print("Enter the movie number: ");
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= movieCount) {
            movies[choice - 1].displayDetails();
            System.out.print("Enter the seat number to cancel booking: ");
            int seatNo = scanner.nextInt();
            if (movies[choice - 1].cancelBooking(seatNo)) {
                System.out.println("Booking canceled successfully!");
            } else {
                System.out.println("Booking cancellation failed. Please try again!");
            }
        } else {
            System.out.println("Invalid movie number!");
        }
    }

    @Override
    public void displayMovieDetails() {
        displayMovies();
        System.out.print("Enter the movie number: ");
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= movieCount) {
            movies[choice - 1].displayDetails();
        } else {
            System.out.println("Invalid movie number!");
        }
    }

    @Override
    public void addNewMovie() {
        scanner.nextLine(); 
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();
        System.out.print("Enter movie genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter movie duration (in minutes): ");
        int duration = scanner.nextInt();
        System.out.print("Enter movie rating: ");
        double rating = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.print("Enter movie date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter movie time (HH:MM): ");
        String time = scanner.nextLine();
        System.out.print("Enter ticket price: Rs. ");
        double ticketPrice = scanner.nextDouble();
        System.out.print("Enter total seats: ");
        int totalSeats = scanner.nextInt();

        addMovie(new RegularMovie(title, genre, duration, rating, ticketPrice, totalSeats, date, time));
        System.out.println("Movie added successfully!");
    }

    public boolean userLogin() {
        scanner.nextLine(); 
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        return user.login(username, password);
    }

    public boolean adminLogin() {
        scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        return admin.login(username, password);
    }

    public static void main(String[] args) {
        MovieBookingSystem bookingSystem = new MovieBookingSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. User Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    if (bookingSystem.userLogin()) {
                        bookingSystem.userMenu();
                    } else {
                        System.out.println("Invalid username or password!");
                    }
                    break;
                case 2:
                    if (bookingSystem.adminLogin()) {
                        bookingSystem.adminMenu();
                    } else {
                        System.out.println("Invalid username or password!");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);
        scanner.close();
    }

    private void userMenu() {
        int choice;
        do {
            System.out.println("\nUser Menu:");
            System.out.println("1. Display Movies");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Display Movie Details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayMovies();
                    break;
                case 2:
                    bookTicket();
                    break;
                case 3:
                    cancelTicket();
                    break;
                case 4:
                    displayMovieDetails();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    private void adminMenu() {
        int choice;
        do {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Display Movies");
            System.out.println("2. Add New Movie");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayMovies();
                    break;
                case 2:
                    addNewMovie();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }
}
