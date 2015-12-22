import scala.math.Ordered.orderingToOrdered

/**
  * master-thesis Clemens Banas
  * Organization: DBIS - University of Innsbruck
  * Created 22.12.15.
  */
class NaiveVariantCallerKey(val sampleIdentifier: Int, val position: Int) extends NaiveVariantCallerSerializableTrait with Ordered[NaiveVariantCallerKey] {

  def getSampleIdentifier(): Int = {
    sampleIdentifier
  }

  def getPosition(): Int = {
    position
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[NaiveVariantCallerKey]

  override def equals(other: Any): Boolean = other match {
    case that: NaiveVariantCallerKey =>
      (that canEqual this) &&
        sampleIdentifier == that.sampleIdentifier &&
        position == that.position
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(sampleIdentifier, position)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def compare(that: NaiveVariantCallerKey): Int = (this.sampleIdentifier, this.position) compare (that.sampleIdentifier, that.position)

  override def toString: String = {
    sampleIdentifier + "," + position
  }

}