package medicalrecords;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private String patientId;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String bloodType;
    private String phoneNumber;
    private String emailAddress;
    private List<Diagnosis> diagnoses;
    private List<Treatment> treatments;
    private List<Prescription> prescriptions;

    public MedicalRecord(String patientId, String name, String dateOfBirth, String gender, String bloodType,
            String phoneNumber, String emailAddress, String diagnosisString,
            String treatmentString, String prescriptionString) {
        this.patientId = patientId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.diagnoses = parseDiagnoses(diagnosisString);
        this.treatments = parseTreatments(treatmentString);
        this.prescriptions = parsePrescriptions(prescriptionString);
    }

    private List<Diagnosis> parseDiagnoses(String diagnosisString) {
        List<Diagnosis> diagnosesList = new ArrayList<>();
        String[] diagnosesArray = diagnosisString.split(";");
        for (String diag : diagnosesArray) {
            Diagnosis diagnosis = Diagnosis.fromCSV(diag);
            diagnosesList.add(diagnosis);
        }
        return diagnosesList;
    }

    private List<Treatment> parseTreatments(String treatmentString) {
        List<Treatment> treatmentsList = new ArrayList<>();
        String[] treatmentsArray = treatmentString.split(";");
        for (String treat : treatmentsArray) {
            Treatment treatment = Treatment.fromCSV(treat);
            treatmentsList.add(treatment);
        }
        return treatmentsList;
    }

    private List<Prescription> parsePrescriptions(String prescriptionString) {
        List<Prescription> prescriptionsList = new ArrayList<>();

        // Split the prescriptions based on a semicolon delimiter
        String[] prescriptionsArray = prescriptionString.split(";");

        for (String presc : prescriptionsArray) {
            try {
                // Use the static factory method fromCSV to create a Prescription
                Prescription prescription = Prescription.fromCSV(presc);
                prescriptionsList.add(prescription);
            } catch (IllegalArgumentException e) {
                // FOR DEBUGGING
                System.out.println("Error parsing prescription: " + e.getMessage());
            }
        }

        return prescriptionsList;
    }

    // Method to view the entire medical record as a formatted string
    public String getMedicalRecordDescription() {
        StringBuilder record = new StringBuilder();
        record.append("Patient ID: ").append(patientId).append("\n")
                .append("Name: ").append(name).append("\n")
                .append("Date of Birth: ").append(dateOfBirth).append("\n")
                .append("Gender: ").append(gender).append("\n")
                .append("Blood Type: ").append(bloodType).append("\n")
                .append("Phone Number: ").append(phoneNumber).append("\n")
                .append("Email Address: ").append(emailAddress).append("\n\n");

        // Add diagnoses string
        record.append("-----Diagnoses-----\n");
        if (diagnoses.isEmpty()) {
            record.append("No diagnoses available.\n");
        } else {
            for (Diagnosis diagnosis : diagnoses) {
                record.append(diagnosis.getDiagnosisDetails()).append("\n\n");
            }
        }

        // Add treatments string
        record.append("-----Treatments-----\n");
        if (treatments.isEmpty()) {
            record.append("No treatments available.\n");
        } else {
            for (Treatment treatment : treatments) {
                record.append(treatment.getTreatment()).append("\n\n");
            }
        }

        // Add prescriptions string
        record.append("-----Prescriptions-----\n");
        if (prescriptions.isEmpty()) {
            record.append("No prescriptions available.\n");
        } else {
            for (Prescription prescription : prescriptions) {
                record.append(prescription.getDescriptionDetails()).append("\n\n");
            }
        }

        return record.toString();
    }

    // Method to add a diagnosis
    public void addDiagnosis(Diagnosis diagnosis) {
        if (diagnosis != null) {
            diagnoses.add(diagnosis);
        }
    }

    // Method to remove a diagnosis
    public void removeDiagnosis(Diagnosis diagnosis) {
        diagnoses.remove(diagnosis);
    }

    // Method to add a treatment
    public void addTreatment(Treatment treatment) {
        if (treatment != null) {
            treatments.add(treatment);
        }
    }

    // Method to remove a treatment
    public void removeTreatment(Treatment treatment) {
        treatments.remove(treatment);
    }

    // Method to add a prescription
    public void addPrescription(Prescription prescription) {
        if (prescription != null) {
            prescriptions.add(prescription);
        }
    }

    // Method to remove a prescription
    public void removePrescription(Prescription prescription) {
        prescriptions.remove(prescription);
    }

    // Allow patients to update contact information
    public void updateContactInfo(String newPhoneNumber, String newEmailAddress) {
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            this.phoneNumber = newPhoneNumber;
        }
        if (newEmailAddress != null && !newEmailAddress.isEmpty()) {
            this.emailAddress = newEmailAddress;
        }
    }

    // Getters for non-modifiable fields
    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    // Getter for prescriptions
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Append basic information
        sb.append(patientId).append(",")
                .append(name).append(",")
                .append(dateOfBirth).append(",")
                .append(gender).append(",")
                .append(bloodType).append(",")
                .append(phoneNumber).append(",")
                .append(emailAddress).append(",");

        // Append diagnoses
        if (diagnoses.isEmpty()) {
            sb.append(""); // Empty if no diagnoses
        } else {
            for (int i = 0; i < diagnoses.size(); i++) {
                sb.append(diagnoses.get(i).toString()); // Assuming toString() is implemented in Diagnosis
                if (i < diagnoses.size() - 1) {
                    sb.append(";"); // Use semicolon as a delimiter between diagnoses
                }
            }
        }
        sb.append(",");

        // Append treatments
        if (treatments.isEmpty()) {
            sb.append(""); // Empty if no treatments
        } else {
            for (int i = 0; i < treatments.size(); i++) {
                sb.append(treatments.get(i).toString()); // Assuming toString() is implemented in Treatment
                if (i < treatments.size() - 1) {
                    sb.append(";"); // Use semicolon as a delimiter between treatments
                }
            }
        }
        sb.append(",");

        // Append prescriptions
        if (prescriptions.isEmpty()) {
            sb.append(""); // Empty if no prescriptions
        } else {
            for (int i = 0; i < prescriptions.size(); i++) {
                sb.append(prescriptions.get(i).toString()); // Assuming toString() is implemented in Prescription
                if (i < prescriptions.size() - 1) {
                    sb.append(";"); // Use semicolon as a delimiter between prescriptions
                }
            }
        }

        return sb.toString();
    }

}
