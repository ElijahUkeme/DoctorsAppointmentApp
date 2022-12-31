# DoctorsAppointmentApp
The project is an Android App whose backend was developed with Java Spring boot
It has the following functionality

PATIENT
Patient will create an account with the app and the app will generate an authentication token to the patient as well as creating a hospital card to the patient.
A card fee will be requested from the patient on arrival to the hospital. The card fee depends on the age of the patient. The age is calculated from taking the date of birth entered by the patient and compare it with LocalDate.now() method. i.e period between date of birth to LocalDate.now. 
That gives us the age of the patient.
Before a patient can book an appointment, he/she must provide the token that was generated to him during the registration process.

The app allows a registered patient to book an appointment with the Doctor. The appointment status at the time of successful booking will be “Pending” until the admin attended to it.
During this process of booking an appointment, the app will generate another token for the patient (i.e appointment token). This token is what the user will use to check the appointment information later on. Therefore, every appointment booked has its own unique token.

The admin will view all the appointments placed by the patient and then assign a doctor to each appointment based on the doctor that will be available at the patient’s preferred date and time. At that moment, the patient’s appointment status will change to “Approved”.
The admin can also change the time and date that was entered by the user if there will be no doctor available at the said date and time.

The admin can also cancel an appointment due to one thing or the other.
The patient will be able to view his/her appointment information by providing the appointment token. This will also help the patient in keeping track of his appointment status
The appointment status is of four types which are;
“Pending” i.e when the booked appointment is not yet been accessed by the admin
“Approved” i.e  After the admin approved a pending appointment
“Cancelled” i.e if the appointment was cancelled by the admin due to some reasons
“Attended” i.e after the doctor must have attended to the patient

During the patient appointment booking process, the app will check if that particular patient has already placed an appointment that’s not yet been attended to. i.e will use the patient id and go to the database that stored all the appointment booked, 
check if that particular patient has an appointment whose status is either “Pending” or “Approved” then throw an exception.

The admin can view the list of all the appointment based on different appointment status.
All the exceptions and validations are handled by the backend in which I developed with Spring boot and the source code can also be found in my github
