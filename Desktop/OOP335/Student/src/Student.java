

// Student.java
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class Student implements Comparable<Student> {
    public enum ClassStanding {
        FRESHMAN, SOPHOMORE, JUNIOR, SENIOR
    }

    private final String firstName;
    private final String lastName;
    private Optional<String> preferredName;
    private final LocalDate birthDate;
    private ClassStanding classStanding;
    private final List<Course> courses;
    private double gpa;

    /**
     * Creates a new Student
     * @param firstName the student's first name
     * @param lastName the student's last name
     * @param birthDate the student's birth date
     * @param standing the student's class standing
     * @pre firstName != null && lastName != null && birthDate != null && standing != null
     */
    public Student(String firstName, String lastName, LocalDate birthDate, String standing) {
        if (firstName == null || lastName == null || birthDate == null || standing == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.preferredName = Optional.empty();
        try {
            this.classStanding = ClassStanding.valueOf(standing.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid class standing");
        }
        this.courses = new ArrayList<>();
    }

    /**
     * @return the student's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the student's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the student's preferred name, if one exists
     */
    public Optional<String> getPreferredName() {
        return preferredName;
    }

    /**
     * Sets the student's preferred name
     * @param name the preferred name
     */
    public void setPreferredName(String name) {
        preferredName = Optional.ofNullable(name);
    }

    /**
     * @return the student's birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * @return the student's age
     */
    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * @return the student's GPA
     */
    public double getGpa() {
        calculateGpa();
        return gpa;
    }

    /**
     * @return an unmodifiable list of all courses
     */
    public List<Course> getAllCourses() {
        return Collections.unmodifiableList(courses);
    }

    /**
     * @return a list of completed courses
     */
    public List<Course> getCompletedCourses() {
        return courses.stream()
                     .filter(Course::isCompleted)
                     .collect(Collectors.toList());
    }

    /**
     * @return a list of current courses
     */
    public List<Course> getCurrentCourses() {
        return courses.stream()
                     .filter(c -> !c.isCompleted())
                     .collect(Collectors.toList());
    }

    /**
     * Adds a course to the student's schedule
     * @param course the course to add
     * @pre course != null
     */
    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        courses.add(course);
    }

    private void calculateGpa() {
        List<Course> completedCourses = getCompletedCourses();
        if (completedCourses.isEmpty()) {
            gpa = 0.0;
            return;
        }

        int totalCredits = 0;
        double totalPoints = 0.0;

        // Create a map to associate letter grades with point values
        Map<Course.LetterGrade, Double> gradePoints = new HashMap<>();
        gradePoints.put(Course.LetterGrade.A, 4.0);
        gradePoints.put(Course.LetterGrade.B, 3.0);
        gradePoints.put(Course.LetterGrade.C, 2.0);
        gradePoints.put(Course.LetterGrade.D, 1.0);
        // 'F' and other grades are not included

        for (Course course : completedCourses) {
            int credits = course.getCreditHours();
            totalCredits += credits;

            Course.LetterGrade letterGrade = course.getLetterGrade(); // Change here
            if (gradePoints.containsKey(letterGrade)) {
                totalPoints += credits * gradePoints.get(letterGrade);
            }
            // No action needed for 'F' or other cases.
        }

        gpa = totalPoints / totalCredits;
    }


    @Override
    public int compareTo(Student other) {
        int lastNameComparison = this.lastName.compareTo(other.lastName);
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }
        
        int firstNameComparison = this.firstName.compareTo(other.firstName);
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }
        
        return this.birthDate.compareTo(other.birthDate);
    }
}