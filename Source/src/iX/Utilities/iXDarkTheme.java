package iX.Utilities;

import java.awt.Color;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

public class iXDarkTheme {

    public DefaultMetalTheme darkTheme = new DefaultMetalTheme() {

        @Override
        public ColorUIResource getPrimary1() {
            return new ColorUIResource(new Color(66, 33, 66));
        }

        @Override
        public ColorUIResource getPrimary2() {
            return new ColorUIResource(new Color(90, 86, 99));
        }

        @Override
        public ColorUIResource getPrimary3() {
            return new ColorUIResource(new Color(99, 99, 99));
        }
        
        public ColorUIResource getSecondary1() {
        	return new ColorUIResource(0, 0, 0);
        }
        public ColorUIResource getSecondary2() {
        	return new ColorUIResource(51, 51, 51);
        }
        public ColorUIResource getSecondary3() {
        	return new ColorUIResource(102, 102, 102);
        }

        @Override
        public ColorUIResource getBlack(){
                    return new ColorUIResource(new Color(222, 222, 222));
                }

        @Override
        public ColorUIResource getWhite() {
            return new ColorUIResource(new Color(0, 0, 0));
        }


        @Override
        public ColorUIResource getMenuForeground() {
            return new ColorUIResource(new Color(200, 200, 200));
        }

        @Override
        public ColorUIResource getMenuBackground() {
            return new ColorUIResource(new Color(25, 25, 25));
        }

         @Override
        public ColorUIResource getMenuSelectedBackground(){
            return new ColorUIResource(new Color(50, 50, 50));
        }

        @Override
        public ColorUIResource getMenuSelectedForeground() {
            return new ColorUIResource(new Color(255, 255, 255));
        }


        @Override
        public ColorUIResource getSeparatorBackground() {
            return new ColorUIResource(new Color(15, 15, 15));
        }


        @Override
        public ColorUIResource getUserTextColor() {
            return new ColorUIResource(new Color(240, 240, 240));
        }

        @Override
        public ColorUIResource getTextHighlightColor() {
            return new ColorUIResource(new Color(80, 40, 80));
        }


        @Override
        public ColorUIResource getAcceleratorForeground(){
            return new ColorUIResource(new Color(30, 30,30));
        }


        @Override
        public ColorUIResource getWindowTitleInactiveBackground() {
            return new ColorUIResource(new Color(30, 30, 30));
        }


        @Override
        public ColorUIResource getWindowTitleBackground() {
            return new ColorUIResource(new Color(30, 30, 30));
        }


        @Override
        public ColorUIResource getWindowTitleForeground() {
            return new ColorUIResource(new Color(230, 230, 230));
        }

        @Override
        public ColorUIResource getPrimaryControlHighlight() {
            return new ColorUIResource(new Color(40, 40, 40));
        }

        @Override
        public ColorUIResource getPrimaryControlDarkShadow() {
            return new ColorUIResource(new Color(40, 40, 40));
        }

        @Override
        public ColorUIResource getPrimaryControl() {
            //color for minimize,maxi,and close
            return new ColorUIResource(new Color(60, 60, 60));
        }

        @Override
        public ColorUIResource getControlHighlight() {
            return new ColorUIResource(new Color(20, 20, 20));
        }

        @Override
        public ColorUIResource getControlDarkShadow() {
            return new ColorUIResource(new Color(50, 50, 50));
        }

        @Override
        public ColorUIResource getControl() {
            return new ColorUIResource(new Color(25, 25, 25));
        }

        @Override
        public ColorUIResource getControlTextColor() {
            return new ColorUIResource(new Color(230, 230, 230));
        }

        @Override
        public ColorUIResource getFocusColor() {
            return new ColorUIResource(new Color(0, 100, 0));
        }

        @Override
        public ColorUIResource getHighlightedTextColor() {
            return new ColorUIResource(new Color(250, 250, 250));
        }

    };

}
