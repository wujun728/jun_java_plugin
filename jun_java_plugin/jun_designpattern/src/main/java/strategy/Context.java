package strategy;

public class Context {

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * @uml.property name="strategy"
	 * @uml.associationEnd inverse="context:strategy.Strategy"
	 */
	private Strategy strategy;

	/**
	 * Getter of the property <tt>strategy</tt>
	 * 
	 * @return Returns the strategy.
	 * @uml.property name="strategy"
	 */
	public Strategy getStrategy() {
		return strategy;
	}

	/**
	 * Setter of the property <tt>strategy</tt>
	 * 
	 * @param strategy
	 *            The strategy to set.
	 * @uml.property name="strategy"
	 */
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public void contextInterface() {
		this.strategy.algorInterface();
	}

}
