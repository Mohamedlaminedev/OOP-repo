

// Assignment.java
public class Assignment {
    private final String name;
    private final double pointsPossible;
    private double pointsEarned;
    private boolean graded;
    private double percentage;

    /**
     * Creates a new Assignment.
     * @param name the name of the assignment
     * @param pointsPossible the total points possible for this assignment
     * @pre name != null && pointsPossible > 0
     */
    public Assignment(String name, double pointsPossible) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (pointsPossible <= 0) {
            throw new IllegalArgumentException("Points possible must be positive");
        }
        this.name = name;
        this.pointsPossible = pointsPossible;
        this.pointsEarned = 0.0;
        this.graded = false;
        this.percentage = 0.0;
    }

    /**
     * @return the name of the assignment
     */
    public String getName() {
        return name;
    }

    /**
     * @return the total points possible for this assignment
     */
    public double getPointsPossible() {
        return pointsPossible;
    }

    /**
     * @return the points earned on this assignment
     */
    public double getPointsEarned() {
        return pointsEarned;
    }

    /**
     * @return whether the assignment has been graded
     */
    public boolean isGraded() {
        return graded;
    }

    /**
     * @return the grade as a percentage
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * Sets the points earned on this assignment
     * @param pointsEarned the points earned
     * @pre pointsEarned >= 0
     */
    public void setPointsEarned(double pointsEarned) {
        if (pointsEarned < 0) {
            throw new IllegalArgumentException("Points earned cannot be negative");
        }
        this.pointsEarned = Math.min(pointsEarned, pointsPossible);
        this.percentage = (this.pointsEarned / pointsPossible) * 100.0;
        this.graded = true;
    }
}