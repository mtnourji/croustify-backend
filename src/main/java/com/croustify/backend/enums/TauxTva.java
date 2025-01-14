package com.croustify.backend.enums;

public enum TauxTva {

        TVA_6(6),
        TVA_10(10),
        TVA_21(21);

        public final double value;

        private TauxTva(double value) {
            this.value = value;
        }
}
