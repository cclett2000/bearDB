package com.bearzwebworks.beardb.util;

import java.time.Year;

public class copyrightHandler {
    public static String getCopyright(){
        String copyright;
        int year = Year.now().getValue();

        if(year > 2023) {
            copyright = "Copyright © 2023-" + year + " Charles Lett Jr. All rights reserved.";
        }
        else{
            copyright = "Copyright © " + year + " Charles Lett Jr. All rights reserved.";
        }

        return copyright;
    }
}
