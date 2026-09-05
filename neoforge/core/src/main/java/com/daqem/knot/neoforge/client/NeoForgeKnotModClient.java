package com.daqem.knot.neoforge.client;

import com.daqem.knot.api.Constants;
import com.daqem.knot.client.KnotModClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeKnotModClient {

    public NeoForgeKnotModClient() {
        KnotModClient.init();
    }
}