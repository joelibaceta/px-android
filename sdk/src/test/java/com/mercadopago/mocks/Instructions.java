package com.mercadopago.mocks;

import com.mercadopago.model.Instruction;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.utils.ResourcesUtil;

/**
 * Created by vaserber on 10/31/17.
 */

public class Instructions {

    private Instructions() {

    }

    public static Instruction getInstructionForBoleto() {
        String json = ResourcesUtil.getStringResource("instruction_boleto.json");
        return JsonUtil.getInstance().fromJson(json, Instruction.class);
    }
}
