//$Id$
package com.zoho.hawking.language.english.tensepredictor.dependencies;

public class Dependencies<ONE, TWO> {
    protected final ONE one;
    protected final TWO two;

    public Dependencies(final ONE one, final TWO two) {
        this.one = one;
        this.two = two;
    }

    public boolean equals(final Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass() && one.equals(((Dependencies<?, ?>) obj).one) && two.equals(((Dependencies<?, ?>) obj).two);
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (one == null ? 0 : one.hashCode());
        return (prime * result + (two == null ? 0 : two.hashCode()));
    }

    public String toString() {
        return String.format("[%s,%s]", one, two); //No I18N
    }
}
