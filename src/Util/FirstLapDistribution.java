package Util;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.RombergIntegrator;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.apache.commons.math3.util.FastMath;

import static Data.GlobalInfo.*;

@SuppressWarnings("deprecation")
public class FirstLapDistribution extends AbstractRealDistribution {
	private int[] gainedPositions;
	private static final double MAX_POSITIONS_LOST = -(getDriverList().size() - 1);
	private static final double MAX_POSITIONS_GAINED = getDriverList().size() - 1;

	/*
	Probability Density Function formula: https://www.desmos.com/calculator/kptertp4f4, where g(n) is the
	inner class FirstLapProbabilityFunction
	 */
	@Override
	public double density(double x) {
		double[] terms = new double[gainedPositions.length];
		for(int i = 0; i < gainedPositions.length; i++){
			double term1 = FastMath.pow(x - new FirstLapProbabilityFunction().value(x), 2);
			double term2 = -term1 / 2;
			terms[i] = FastMath.pow(FastMath.E, term2);
		}
		double term3 = new Sum().evaluate(terms, 0, terms.length);
		double scalar = 1 / (4 * FastMath.sqrt(2 * FastMath.PI));
		return scalar * term3;
	}

	// Cumulative Probability Function is the antiderivative of the Probability Distribution Function
	@Override
	public double cumulativeProbability(double x) {
		return new RombergIntegrator().integrate(MaxEval.unlimited().getMaxEval(),
				new FirstLapProbabilityFunction(), Double.NEGATIVE_INFINITY, x);
	}

	/*
	Formulae for numerical mean and variance: https://amsi.org.au/ESA_Senior_Years/SeniorTopic4/4e/4e_2content_4.html
	 */

	@Override
	public double getNumericalMean() {
		return new RombergIntegrator().integrate(MaxEval.unlimited().getMaxEval(),
				x -> (x * new FirstLapProbabilityFunction().value(x)),
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	@Override
	public double getNumericalVariance() {
		return new RombergIntegrator().integrate(MaxEval.unlimited().getMaxEval(),
				x -> (FastMath.pow(x - getNumericalMean(), 2) *
						new FirstLapProbabilityFunction().value(x)),
				Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	/*
	Support bounds are the most positions that can be gained or lost (bounds of the set
	of possible values)

	Source: https://en.wikipedia.org/wiki/Support_(mathematics)#In_probability_and_measure_theory
	 */

	@Override
	public double getSupportLowerBound() {
		return MAX_POSITIONS_LOST;
	}

	@Override
	public double getSupportUpperBound() {
		return MAX_POSITIONS_GAINED;
	}

	/*
	The most positions that can be gained or lost are possible values (i.e. it is possible to lose
	or gain that many places), so they are included in the distribution.

	All values between the bounds are possible values.
	 */

	@Override
	public boolean isSupportLowerBoundInclusive() {
		return true;
	}

	@Override
	public boolean isSupportUpperBoundInclusive() {
		return true;
	}

	@Override
	public boolean isSupportConnected() {
		return true;
	}

	public int getGainedPositions(int i) {
		return gainedPositions[i];
	}

	public void setGainedPositions(int[] gainedPositions) {
		this.gainedPositions = gainedPositions;
	}

	/*
	This class represents g(x) in the probability distribution function (PDF) given by https://www.desmos.com/calculator/kptertp4f4
	 */
	class FirstLapProbabilityFunction implements UnivariateFunction {
		@Override
		public double value(double x) {
			return (int) x == x ? getGainedPositions((int) x) : 0;
		}
	}
}
