package com.college.student.pojo;

import java.io.Serializable;
import java.util.Objects;

public class Admission implements Cloneable, Serializable, Comparable<Admission> {
    private String course;
    private int section;
    private int admissionYear;
    private int rollNo;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    @Override
    public String toString() {
        return "Admission{" +
                "course='" + course + '\'' +
                ", section=" + section +
                ", admissionYear=" + admissionYear +
                ", rollNo=" + rollNo +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Admission admission = (Admission) object;
        return section == admission.section && admissionYear == admission.admissionYear && rollNo == admission.rollNo && Objects.equals(course, admission.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, section, admissionYear, rollNo);
    }

    @Override
    public int compareTo(Admission o) {
        return Integer.compare(o.getRollNo(), rollNo);
    }
}
