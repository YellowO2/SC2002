package database;

import users.Doctor;
import users.Patient;
import users.Pharmacist;
import users.User;
import medicalrecords.MedicalRecord;
import java.io.*;
import java.util.*;

public class HMSDatabase {
    public static final String SEPARATOR = ",";

    // Singleton instance
    private static HMSDatabase instance;

    // Data fields to store all users and medical records
    private List<User> users;
    private List<MedicalRecord> medicalRecords;

    // Private constructor to prevent instantiation
    private HMSDatabase() {
        users = new ArrayList<>();
        medicalRecords = new ArrayList<>();
    }

    // Static method to provide access to the single instance
    public static HMSDatabase getInstance() {
        if (instance == null) {
            instance = new HMSDatabase();
        }
        return instance;
    }

    // Load data from CSV files into memory
    public void initializeDatabase() throws IOException {
        loadUsers("csv_data/User_List.csv"); // Load users from the single user list
        loadMedicalRecords("csv_data/Medical_Record.csv");
    }

    // Load users (patients, staff, etc.) based on role from the CSV file
    private void loadUsers(String filename) throws IOException {
        List<String> lines = readFile(filename);

        // Skip the header by starting from 1
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] tokens = line.split(SEPARATOR);

            if (tokens.length == 8) { // Ensure all necessary fields are present
                String id = tokens[0];
                String name = tokens[1];
                String dob = tokens[2]; // Date of birth
                String gender = tokens[3];
                String phoneNumber = tokens[4];
                String emailAddress = tokens[5];
                String password = tokens[6];
                String role = tokens[7];

                System.out.println(id + " " + name + " " + dob + " " + gender + " " + phoneNumber + " " + emailAddress
                        + " " + password + " " + role);

                // Based on the role, create specific User objects
                switch (role) {
                    // Order of params: ID, Name, Date of Birth, Gender, Phone Number, Email
                    // Address,
                    // Password, Role
                    case "Patient":
                        Patient patient = new Patient(id, name, dob, gender, phoneNumber, emailAddress, password);
                        users.add(patient);
                        break;

                    case "Doctor":
                        Doctor doctor = new Doctor(id, name, dob, gender, phoneNumber, emailAddress, password);
                        users.add(doctor);
                        break;

                    case "Pharmacist":
                        Pharmacist pharmacist = new Pharmacist(id, name, dob, gender, phoneNumber, emailAddress,
                                password);
                        users.add(pharmacist);
                        break;

                    default:
                        System.out.println("Unknown user role for ID: " + id);
                        break;
                }
            } else {
                System.out.println("Invalid line in CSV: " + line);
            }
        }
    }

    private void loadMedicalRecords(String filename) throws IOException {
        List<String> lines = readFile(filename);
        for (String line : lines) {
            String[] tokens = line.split(SEPARATOR);
            MedicalRecord record = new MedicalRecord(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5],
                    tokens[6]);
            medicalRecords.add(record);
        }
    }

    // Save medical records back to the CSV file
    public void saveMedicalRecords() throws IOException {
        saveData("csv_data/Medical_Record.csv", medicalRecords);
    }

    private void saveData(String filename, List<?> dataList) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        try {
            for (Object obj : dataList) {
                out.println(obj.toString()); // Ensure that User and MedicalRecord have proper toString methods
            }
        } finally {
            out.close();
        }
    }

    private List<String> readFile(String filename) throws IOException {
        List<String> data = new ArrayList<>();
        Scanner scanner = new Scanner(new FileInputStream(filename));
        try {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
        return data;
    }

    // Getter methods for users and medical records
    public List<User> getUsers() {
        return users;
    }

    public User getUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
}
