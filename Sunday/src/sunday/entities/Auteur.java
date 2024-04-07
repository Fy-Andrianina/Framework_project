package sunday.entities;
import veda.godao.annotations.Column;
import veda.godao.annotations.Table;
import veda.godao.annotations.PrimaryKey;

@Table("auteur")

public class Auteur {
    @PrimaryKey
@Column("id")
private Integer id;
public Integer getId(){ return id; }
public void setId(Integer o){ id=o; }
@Column("label")
private String label;
public String getLabel(){ return label; }
public void setLabel(String o){ label=o; }

    public Auteur(){}
public Auteur(Integer o){ id=o; }

}

