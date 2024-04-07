package my_boo.entities;
import veda.godao.annotations.Column;
import veda.godao.annotations.Table;
import veda.godao.annotations.PrimaryKey;

@Table("livre")

public class Livre {
    @PrimaryKey
@Column("id")
private Integer id;
public Integer getId(){ return id; }
public void setId(Integer o){ id=o; }
@Column("label")
private String label;
public String getLabel(){ return label; }
public void setLabel(String o){ label=o; }
@veda.godao.annotations.ForeignKey(recursive=true)
@Column("auteur")
private Auteur auteur;
public Auteur getAuteur(){ return auteur; }
public void setAuteur(Auteur o){ auteur=o; }
@veda.godao.annotations.ForeignKey(recursive=true)
@Column("editeur")
private Editeur editeur;
public Editeur getEditeur(){ return editeur; }
public void setEditeur(Editeur o){ editeur=o; }
@Column("date_publication")
private java.time.LocalDate datePublication;
public java.time.LocalDate getDatePublication(){ return datePublication; }
public void setDatePublication(java.time.LocalDate o){ datePublication=o; }

    public Livre(){}
public Livre(Integer o){ id=o; }

}

