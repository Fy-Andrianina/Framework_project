package sunday.entities;
import veda.godao.annotations.Column;
import veda.godao.annotations.Table;
import veda.godao.annotations.PrimaryKey;

@Table("editeur")

public class Editeur {
    @PrimaryKey
@Column("id")
private Integer id;
public Integer getId(){ return id; }
public void setId(Integer o){ id=o; }
@Column("label")
private String label;
public String getLabel(){ return label; }
public void setLabel(String o){ label=o; }

    public Editeur(){}
public Editeur(Integer o){ id=o; }

}

