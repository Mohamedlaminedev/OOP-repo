

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class Course implements Comparable<Course> {
    public enum Semester {
        SPRING, SUMMER, FALL
    }

    public enum LetterGrade {
        A, B, C, D, E; 

        public static LetterGrade fromPercentage(double percentage) {
            if (percentage >= 90.0) return A;
            else if (percentage >= 80.0) return B;
            else if (percentage >= 70.0) return C;
            else if (percentage >= 60.0) return D;
            else return E; // Return E for below 60%
        }
    }

    private final String courseNum;
    private final Semester semester;
    private final String year;
    private int creditHours;
    private final List<Assignment> assignments;
    private boolean completed;
    private double percentage;
    private LetterGrade letterGrade; // Change from char to LetterGrade

    /**
     * Creates a new Course
     * @param courseNum the course number (e.g. CSc 335)
     * @param semester the semester (Spring, Summer, or Fall)
     * @param year the year
     * @param credits the number of credit hours
     * @pre courseNum != null && semester != null && year != null && credits > 0
     */
    public Course(String courseNum, String semester, String year, int credits) {
        if (courseNum == null || semester == null || year == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be positive");
        }
        
        this.courseNum = courseNum;
        try {
            this.semester = Semester.valueOf(semester.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid semester. Must be Spring, Summer, or Fall");
        }
        this.year = year;
        this.creditHours = credits;
        this.assignments = new ArrayList<>();
        this.completed = false;
    }

    /**
     * @return the number of credit hours
     */
    public int getCreditHours() {
        return creditHours;
    }

    /**
     * Sets the number of credit hours
     * @param credits the new number of credit hours
     * @pre credits > 0
     */
    public void setCreditHours(int credits) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be positive");
        }
        this.creditHours = credits;
    }

    /**
     * @return the current grade as a percentage
     */
    public double getPercentage() {
        calculatePercentage();
        return percentage;
    }

    /**
     * @return the current letter grade
     */
    public LetterGrade getLetterGrade() {
        calculateLetterGrade();
        return letterGrade; // Change to return LetterGrade
    }

    /**
     * @return whether the course is completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Marks the course as completed
     */
    public void setCompleted() {
        this.completed = true;
    }

    /**
     * Adds an assignment to the course
     * @param assignment the assignment to add
     * @pre assignment != null
     */
    public void addAssignment(Assignment assignment) {
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment cannot be null");
        }
        assignments.add(assignment);
    }

    /**
     * @return an unmodifiable list of assignments
     */
    public List<Assignment> getAssignments() {
        return Collections.unmodifiableList(assignments);
    }

    private void calculatePercentage() {
        double pointsPossible = 0.0;
        double pointsEarned = 0.0;
        for (Assignment assignment : assignments) {
            if (assignment.isGraded()) {
                pointsPossible += assignment.getPointsPossible();
                pointsEarned += assignment.getPointsEarned();
            }
        }
        this.percentage = pointsPossible == 0 ? 0.0 : (pointsEarned / pointsPossible) * 100.0;
    }

    private void calculateLetterGrade() {
        calculatePercentage();
        this.letterGrade = LetterGrade.fromPercentage(percentage); // Update to use enum
    }

    @Override
    public int compareTo(Course other) {
        return this.courseNum.compareTo(other.courseNum);
    }

    /**
     * Comparator for chronological ordering of courses
     */
    public static final Comparator<Course> CHRONOLOGICAL_COMPARATOR = 
        Comparator.comparing(Course::getYear)
                 .thenComparing(Course::getSemester);

    public String getYear() {
        return year;
    }

    public Semester getSemester() {
        return semester;
    }
}
