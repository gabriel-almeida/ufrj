package modelo;

import java.io.Serializable;
import java.util.Collection;

public interface Dialogo extends Serializable {
	String falaInicial();
	String interagir(String comando);
	Collection<String> opcoes();
}
