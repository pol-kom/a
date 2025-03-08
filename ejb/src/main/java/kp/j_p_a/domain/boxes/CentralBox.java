package kp.j_p_a.domain.boxes;

import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import kp.j_p_a.domain.components.CardinalDirection;
import kp.j_p_a.domain.components.TermDates;

/**
 * The <b>central box</b> entity class.
 * <p>
 * If the relationship is bidirectional, the non-owning side must use the <b>mappedBy</b> element.
 * </p>
 */
@Entity
@NamedQuery(name = "CentralBox.findAll", query = """
		SELECT DISTINCT cb
		FROM CentralBox cb LEFT JOIN FETCH cb.multiBoxes LEFT JOIN FETCH cb.lowerBoxes
		ORDER BY cb.identifier""")
@NamedQuery(name = "CentralBox.findFirst", query = """
		SELECT DISTINCT cb
		FROM CentralBox cb LEFT JOIN FETCH cb.multiBoxes LEFT JOIN FETCH cb.lowerBoxes
		WHERE cb.identifier = (SELECT MIN(cbm.id) FROM CentralBox cbm)""")
public class CentralBox extends Box {

	@Embedded
	private TermDates termDates;

	@Enumerated(EnumType.STRING)
	private CardinalDirection cardinalDirection = CardinalDirection.NORTH;

	// the owning side of bidirectional relationship
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	private UpperBox upperBox;

	// the owning side of bidirectional relationship
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private SingleBox singleBox;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@OrderBy
	private final Set<MultiBox> multiBoxes = new TreeSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy
	private final Set<LowerBox> lowerBoxes = new TreeSet<>();

	/**
	 * Default constructor.
	 */
	public CentralBox() {
		// constructor is empty
	}

	/**
	 * Gets the {@link TermDates}.
	 * 
	 * @return the {@link TermDates}
	 */
	public TermDates getTermDates() {
		return termDates;
	}

	/**
	 * Sets the {@link TermDates}.
	 * 
	 * @param termDates the {@link TermDates} to set
	 */
	public void setTermDates(TermDates termDates) {
		this.termDates = termDates;
	}

	/**
	 * Gets the {@link CardinalDirection}.
	 * 
	 * @return the {@link CardinalDirection}
	 */
	public CardinalDirection getCardinalDirection() {
		return cardinalDirection;
	}

	/**
	 * Sets the {@link CardinalDirection}.
	 * 
	 * @param cardinalDirection the {@link CardinalDirection} to set
	 */
	public void setCardinalDirection(CardinalDirection cardinalDirection) {
		this.cardinalDirection = cardinalDirection;
	}

	/**
	 * Adds the {@link MultiBox}.
	 * 
	 * @param multiBox the {@link MultiBox} to add
	 */
	public void addMultiBox(MultiBox multiBox) {

		multiBoxes.add(multiBox);
		multiBox.getCentralBoxes().add(this);
	}

	/**
	 * Removes the {@link MultiBox}.
	 * 
	 * @param multiBox the {@link MultiBox} to remove
	 */
	public void removeMultiBox(MultiBox multiBox) {

		multiBoxes.remove(multiBox);
		multiBox.getCentralBoxes().remove(this);
	}

	/**
	 * Adds the {@link LowerBox}.
	 * 
	 * @param lowerBox the {@link LowerBox} to add
	 */
	public void addLowerBox(LowerBox lowerBox) {

		lowerBoxes.add(lowerBox);
		lowerBox.setCentralBox(this);
	}

	/**
	 * Removes the {@link LowerBox}.
	 * 
	 * @param lowerBox the {@link LowerBox} to remove
	 */
	public void removeLowerBox(LowerBox lowerBox) {

		lowerBoxes.remove(lowerBox);
		lowerBox.setCentralBox(null);
	}

	/**
	 * Gets the {@link UpperBox}.
	 * 
	 * @return the {@link UpperBox}
	 */
	public UpperBox getUpperBox() {
		return upperBox;
	}

	/**
	 * Sets the {@link UpperBox}.
	 * 
	 * @param upperBox the {@link UpperBox} to set
	 */
	public void setUpperBox(UpperBox upperBox) {
		this.upperBox = upperBox;
	}

	/**
	 * Gets the {@link SingleBox}.
	 * 
	 * @return the {@link SingleBox}
	 */
	public SingleBox getSingleBox() {
		return singleBox;
	}

	/**
	 * Sets the {@link SingleBox}.
	 * 
	 * @param singleBox the {@link SingleBox} to set
	 */
	public void setSingleBox(SingleBox singleBox) {
		this.singleBox = singleBox;
	}

	/**
	 * Gets the {@link MultiBox} set.
	 * 
	 * @return the {@link MultiBox} set
	 */
	public Set<MultiBox> getMultiBoxes() {
		return multiBoxes;
	}

	/**
	 * Gets the {@link LowerBox} set
	 * 
	 * @return the {@link LowerBox} set
	 */
	public Set<LowerBox> getLowerBoxes() {
		return lowerBoxes;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cardinalDirection == null) ? 0 : cardinalDirection.hashCode());
		result = prime * result + ((termDates == null) ? 0 : termDates.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CentralBox other = (CentralBox) obj;
		if (cardinalDirection != other.cardinalDirection)
			return false;
		if (termDates == null) {
			return other.termDates == null;
		}
		return termDates.equals(other.termDates);
	}

}