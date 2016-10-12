/*
 * Code author Solonin Ivan (osmos_two@rambler.ru)
 */
package ru.intech.test.dbobjects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "users")
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
//    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
//    @NamedQuery(name = "Users.findByMsisdn", query = "SELECT u FROM Users u WHERE u.msisdn = :msisdn")})

// ТЗ гласит, что мы любин нативные запросы, так что, любезно сгенерированный Netbeans'ом, JPQL уходит в комменты
// А вместо него добавляется аннотация позволяющая не конвертировать Object'ы, полученные посредством getResultList, в нужный класс
@SqlResultSetMapping (
    name="usersMap",
    classes={
       @ConstructorResult(targetClass=ru.intech.test.dbobjects.Users.class,
            columns={
                @ColumnResult(name="id"),
                @ColumnResult(name="msisdn")
            }
       )
    }
)
public class Users implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    @OrderBy("id DESC")
    private Collection<Melodies> melodiesCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "msisdn")
    private String msisdn;
    
    @Transient
    //Тут будет храниться время начала сессии
    private Date lastTimeSessionActive;
    
    @Transient
    //Идентификатор сессии
    private String uuid;

    public Users() {
    }

    public Users(Integer id) {
        this.id = id;
    }

    public Users(Integer id, String msisdn) {
        this.id = id;
        this.msisdn = msisdn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.intech.test.dbobjects.Users[ id=" + id + " ]";
    }

    public Date getLastTimeSessionActive() {
        return lastTimeSessionActive;
    }

    public void setLastTimeSessionActive(Date lastTimeSessionActive) {
        this.lastTimeSessionActive = lastTimeSessionActive;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @XmlTransient
    public Collection<Melodies> getMelodiesCollection() {
        return melodiesCollection;
    }

    public void setMelodiesCollection(Collection<Melodies> melodiesCollection) {
        this.melodiesCollection = melodiesCollection;
    }
    
}
