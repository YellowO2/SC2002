package users;

import java.util.ArrayList;
import java.util.List;
import inventory.Inventory;
import inventory.Medicine;
import appointments.AppointmentOutcomeRecord;
import medicalrecords.Prescription;

public class Pharmacist extends User {
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords;
    private Inventory inventory;

    public Pharmacist(String id, String name, String dateOfBirth, String gender, String phoneNumber,
            String emailAddress, String password)  {
        super(id, name, "PHARMACIST", password, phoneNumber, emailAddress, dateOfBirth, gender);
        this.appointmentOutcomeRecords = new ArrayList<>();
        this.inventory = new Inventory();
    }

    // Method to view all appointment outcome records
    public void viewAppointmentOutcomeRecords() {
        if (appointmentOutcomeRecords.isEmpty()) {
            System.out.println("No appointment outcome records available.");
        } else {
            for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
                System.out.println(record);
            }
        }
    }

    // Method to update prescription status in appointment outcome records
    public void updatePrescriptionStatus(String medicationName, int newStatus) {
        for (AppointmentOutcomeRecord record : appointmentOutcomeRecords) {
            for (Prescription medication : record.getPrescriptions()) {
                if (medication.getMedicationName().equals(medicationName)) {
                    medication.setStatus(newStatus);
                    System.out.println("Updated status for " + medicationName + " to " + newStatus);
                    return;
                }
            }
        }
        System.out.println("Medication not found in records.");
    }

    // Method to view medication inventory levels
    public void viewMedicationInventory() {
        inventory.displayInventory();
    }

    // Method to submit a replenishment request if stock is low
    public void submitReplenishmentRequest(String medicationId, int quantity) {
        Medicine medicine = inventory.getMedicineById(medicationId);
        if (medicine != null && medicine.isStockLow()) {
            System.out.println("Replenishment request submitted for " + quantity + " units of " + medicine.getName());
        } else {
            System.out.println("Stock levels for " + (medicine != null ? medicine.getName() : "specified medicine") + " are sufficient.");
        }
    }
}