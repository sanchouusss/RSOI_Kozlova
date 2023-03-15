package project.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class BaseScholarship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "value_scholarship")
    private float valueScholarship;

    @Column(name = "value_bpm")
    private float valueBPM;

    @Column(name = "ratio_bpm")
    private float ratioBPM;

}
