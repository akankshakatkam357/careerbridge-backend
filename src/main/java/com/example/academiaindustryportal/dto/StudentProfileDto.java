package com.example.academiaindustryportal.dto;

public class StudentProfileDto {
    private Long id;
    private Long studentId;
    private String department;
    private String program;

    public StudentProfileDto() {
    }

    public StudentProfileDto(Long id, Long studentId, String department, String program) {
        this.id = id;
        this.studentId = studentId;
        this.department = department;
        this.program = program;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
