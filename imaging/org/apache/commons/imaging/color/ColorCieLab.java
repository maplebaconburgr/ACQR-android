package org.apache.commons.imaging.color;

import java.io.Serializable;

import baron.dino.interfaces.EuclideanPoint;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Heavily modified by @author Dino Baron
 * @date 27/09/13
 */


public final class ColorCieLab implements Serializable, EuclideanPoint{
    /**
	 * Serializable added by Dino Baron
	 * Sept 21, 2013
	 */
	private static final long serialVersionUID = -5216025925733716767L;
	public final double L, a, b;

    public ColorCieLab(final double l, final double a, final double b) {
        L = l;
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "{L: " + L + ", a: " + a + ", b: " + b + "}";
    }

    /**
     * Returns the vector [L, a, b]
     */
	@Override
	public double[] getCoordinates() {
		return new double[]{L, a, b};
	}

	/**
	 * Finds the distance to another point in R^3
	 * It doesn't have to make sense, as long as it is consistent
	 * The idea being that we can compare colour closeness using this
	 * 
	 * We aren't using arbitrary precision decimals because 2 colours in the Lab space
	 * should be close enough for it not to matter
	 * It would be nice to have, though...
	 */
	@Override
	public double getDistance(EuclideanPoint point) {
		double[] pointCoords = point.getCoordinates();
		if(pointCoords.length != 3){
			throw new UnsupportedOperationException("Can only compare distance in R^3");
		} else {
			double deltaLSquared = Math.pow(L - pointCoords[0], 2);
			double deltaASquared = Math.pow(a - pointCoords[1], 2);
			double deltaBSquared = Math.pow(b - pointCoords[2], 2);
			return Math.sqrt(deltaLSquared + deltaASquared + deltaBSquared);
		}
	}
    
    
}
