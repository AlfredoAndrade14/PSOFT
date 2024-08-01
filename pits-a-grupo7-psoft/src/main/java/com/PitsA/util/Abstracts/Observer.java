package com.PitsA.util.Abstracts;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Type;


@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@Entity
public abstract class Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    public abstract void update(Object object);
}
