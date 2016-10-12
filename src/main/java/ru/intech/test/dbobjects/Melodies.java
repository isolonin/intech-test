/*
 * Code author Solonin Ivan (osmos_two@rambler.ru)
 */
package ru.intech.test.dbobjects;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "melodies")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Melodies.findAll", query = "SELECT m FROM Melodies m"),
//    @NamedQuery(name = "Melodies.findById", query = "SELECT m FROM Melodies m WHERE m.id = :id"),
//    @NamedQuery(name = "Melodies.findByName", query = "SELECT m FROM Melodies m WHERE m.name = :name")})
@SqlResultSetMapping (
    name="melodiesMap",
    classes={
       @ConstructorResult(targetClass=ru.intech.test.dbobjects.Melodies.class,
            columns={
                @ColumnResult(name="id"),
                @ColumnResult(name="name"),
                @ColumnResult(name="userId")
            }
       )
    }
)
public class Melodies implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MelodiesId")
    @SequenceGenerator(name="MelodiesId", sequenceName="seq_melodies", allocationSize = 1)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users userId;

    public Melodies() {
    }

    public Melodies(Integer id) {
        this.id = id;
    }

    public Melodies(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Melodies)) {
            return false;
        }
        Melodies other = (Melodies) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.intech.test.dbobjects.Melodies[ id=" + id + " ]";
    }
    
}
