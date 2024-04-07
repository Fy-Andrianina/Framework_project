package sunday.entities;
import veda.godao.annotations.Column;
import veda.godao.annotations.Table;
import veda.godao.annotations.PrimaryKey;

@Table("utilisateur")

public class Utilisateur {
    @PrimaryKey
@Column("id")
private Integer id;
public Integer getId(){ return id; }
public void setId(Integer o){ id=o; }
@Column("login")
private String login;
public String getLogin(){ return login; }
public void setLogin(String o){ login=o; }
@Column("pwd")
private String pwd;
public String getPwd(){ return pwd; }
public void setPwd(String o){ pwd=o; }

    public Utilisateur(){}
public Utilisateur(Integer o){ id=o; }

}

