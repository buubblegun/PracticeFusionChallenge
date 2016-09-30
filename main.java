import com.sun.tools.javac.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class main {
    public Map<Specialty, List<Doctor>> doctors;
    public Doctor currentDoctor;


    public Doctor[] getSimilarDoctors(Doctor doctor) {
        currentDoctor = doctor;
        List<Doctor> doctorList = doctors.get(currentDoctor.specialty);
        PriorityQueue<Doctor> closestDoctorQueue = new PriorityQueue<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Doctor d1 = (Doctor) o1;
                Doctor d2 = (Doctor) o2;

                if (d1.calculateDistance(currentDoctor) < d2.calculateDistance(currentDoctor)) {
                    return 1;
                } else if (d1.calculateDistance(currentDoctor) > d2.calculateDistance(currentDoctor)) {
                    return -1;
                } else {
                    if (d1.rating <= d2.rating) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });

        closestDoctorQueue.addAll(doctorList);
        return closestDoctorQueue.toArray(new Doctor[doctorList.size()]);
    }


    public class Doctor {
        Long id;
        String name;
        Specialty specialty;
        Location location;
        Double rating;

        public Doctor(Long id, String name, Specialty specialty, Location location, Double rating) {
            this.id = id;
            this.name = name;
            this.specialty = specialty;
            this.location = location;
            this.rating = rating;
        }

        public double calculateDistance(Doctor doctor) {
            return Math.pow(Math.pow(location.x - doctor.location.x, 2) + Math.pow(location.y - doctor.location.y, 2), 0.5);
        }
    }

    public class Location {
        double x;
        double y;

        public Location(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    enum Specialty {
        Cardiology, Surgery, Pediatrics, Pathology, FamilyMed //whatever else
    }

    public void getSimilarDoctorTest() {
        Doctor d1 = new Doctor(1L, "doc1", Specialty.Cardiology, new Location(0, 0), 10D);
        Doctor d2 = new Doctor(2L, "doc2", Specialty.Pathology, new Location(3, 4), 10D);
        Assert.check(d1.calculateDistance(d2) == 5D);
        Assert.check(d1.calculateDistance(d1) == 0D);

        Doctor[] d1Similar = getSimilarDoctors(d1);
        Doctor[] d2Similar = getSimilarDoctors(d2);

        Double dist = -1D;
        for (Doctor d : d1Similar) {
            Assert.check(d.specialty.equals(d1.specialty));
            Double curDist = d.calculateDistance(d1);
            Assert.check(dist <= curDist);
            dist = curDist;
        }

        dist = -1D;
        for (Doctor d : d2Similar) {
            Assert.check(d.specialty.equals(d2.specialty));
            Double curDist = d.calculateDistance(d2);
            Assert.check(dist <= curDist);
            dist = curDist;
        }
    }
}

