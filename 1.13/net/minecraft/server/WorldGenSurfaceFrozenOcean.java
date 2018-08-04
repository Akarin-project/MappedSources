package net.minecraft.server;

import java.util.Random;

public class WorldGenSurfaceFrozenOcean implements WorldGenSurface<WorldGenSurfaceConfigurationBase> {

    protected static final IBlockData a = Blocks.PACKED_ICE.getBlockData();
    protected static final IBlockData b = Blocks.SNOW_BLOCK.getBlockData();
    private static final IBlockData c = Blocks.AIR.getBlockData();
    private static final IBlockData d = Blocks.GRAVEL.getBlockData();
    private static final IBlockData e = Blocks.ICE.getBlockData();
    private NoiseGenerator3 f;
    private NoiseGenerator3 g;
    private long h;

    public WorldGenSurfaceFrozenOcean() {}

    public void a(Random random, IChunkAccess ichunkaccess, BiomeBase biomebase, int i, int j, int k, double d0, IBlockData iblockdata, IBlockData iblockdata1, int l, long i1, WorldGenSurfaceConfigurationBase worldgensurfaceconfigurationbase) {
        double d1 = 0.0D;
        double d2 = 0.0D;
        BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
        float f = biomebase.c((BlockPosition) blockposition_mutableblockposition.c(i, 63, j));
        double d3 = Math.min(Math.abs(d0), this.f.a((double) i * 0.1D, (double) j * 0.1D));

        if (d3 > 1.8D) {
            double d4 = 0.09765625D;
            double d5 = Math.abs(this.g.a((double) i * 0.09765625D, (double) j * 0.09765625D));

            d1 = d3 * d3 * 1.2D;
            double d6 = Math.ceil(d5 * 40.0D) + 14.0D;

            if (d1 > d6) {
                d1 = d6;
            }

            if (f > 0.1F) {
                d1 -= 2.0D;
            }

            if (d1 > 2.0D) {
                d2 = (double) l - d1 - 7.0D;
                d1 += (double) l;
            } else {
                d1 = 0.0D;
            }
        }

        int j1 = i & 15;
        int k1 = j & 15;
        IBlockData iblockdata2 = biomebase.r().b();
        IBlockData iblockdata3 = biomebase.r().a();
        int l1 = (int) (d0 / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int i2 = -1;
        int j2 = 0;
        int k2 = 2 + random.nextInt(4);
        int l2 = l + 18 + random.nextInt(10);

        for (int i3 = Math.max(k, (int) d1 + 1); i3 >= 0; --i3) {
            blockposition_mutableblockposition.c(j1, i3, k1);
            if (ichunkaccess.getType(blockposition_mutableblockposition).isAir() && i3 < (int) d1 && random.nextDouble() > 0.01D) {
                ichunkaccess.a((BlockPosition) blockposition_mutableblockposition, WorldGenSurfaceFrozenOcean.a, false);
            } else if (ichunkaccess.getType(blockposition_mutableblockposition).getMaterial() == Material.WATER && i3 > (int) d2 && i3 < l && d2 != 0.0D && random.nextDouble() > 0.15D) {
                ichunkaccess.a((BlockPosition) blockposition_mutableblockposition, WorldGenSurfaceFrozenOcean.a, false);
            }

            IBlockData iblockdata4 = ichunkaccess.getType(blockposition_mutableblockposition);

            if (iblockdata4.isAir()) {
                i2 = -1;
            } else if (iblockdata4.getBlock() == iblockdata.getBlock()) {
                if (i2 == -1) {
                    if (l1 <= 0) {
                        iblockdata3 = WorldGenSurfaceFrozenOcean.c;
                        iblockdata2 = iblockdata;
                    } else if (i3 >= l - 4 && i3 <= l + 1) {
                        iblockdata3 = biomebase.r().a();
                        iblockdata2 = biomebase.r().b();
                    }

                    if (i3 < l && (iblockdata3 == null || iblockdata3.isAir())) {
                        if (biomebase.c((BlockPosition) blockposition_mutableblockposition.c(i, i3, j)) < 0.15F) {
                            iblockdata3 = WorldGenSurfaceFrozenOcean.e;
                        } else {
                            iblockdata3 = iblockdata1;
                        }
                    }

                    i2 = l1;
                    if (i3 >= l - 1) {
                        ichunkaccess.a((BlockPosition) blockposition_mutableblockposition, iblockdata3, false);
                    } else if (i3 < l - 7 - l1) {
                        iblockdata3 = WorldGenSurfaceFrozenOcean.c;
                        iblockdata2 = iblockdata;
                        ichunkaccess.a((BlockPosition) blockposition_mutableblockposition, WorldGenSurfaceFrozenOcean.d, false);
                    } else {
                        ichunkaccess.a((BlockPosition) blockposition_mutableblockposition, iblockdata2, false);
                    }
                } else if (i2 > 0) {
                    --i2;
                    ichunkaccess.a((BlockPosition) blockposition_mutableblockposition, iblockdata2, false);
                    if (i2 == 0 && iblockdata2.getBlock() == Blocks.SAND && l1 > 1) {
                        i2 = random.nextInt(4) + Math.max(0, i3 - 63);
                        iblockdata2 = iblockdata2.getBlock() == Blocks.RED_SAND ? Blocks.RED_SANDSTONE.getBlockData() : Blocks.SANDSTONE.getBlockData();
                    }
                }
            } else if (iblockdata4.getBlock() == Blocks.PACKED_ICE && j2 <= k2 && i3 > l2) {
                ichunkaccess.a((BlockPosition) blockposition_mutableblockposition, WorldGenSurfaceFrozenOcean.b, false);
                ++j2;
            }
        }

    }

    public void a(long i) {
        if (this.h != i || this.f == null || this.g == null) {
            SeededRandom seededrandom = new SeededRandom(i);

            this.f = new NoiseGenerator3(seededrandom, 4);
            this.g = new NoiseGenerator3(seededrandom, 1);
        }

        this.h = i;
    }
}
