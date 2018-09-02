package net.minecraft.server;

public class Navigation extends NavigationAbstract {
    private boolean p;

    public Navigation(EntityInsentient entityinsentient, World world) {
        super(entityinsentient, world);
    }

    protected Pathfinder a() {
        this.o = new PathfinderNormal();
        this.o.a(true);
        return new Pathfinder(this.o);
    }

    protected boolean b() {
        return this.a.onGround || this.r() || this.a.isPassenger();
    }

    protected Vec3D c() {
        return new Vec3D(this.a.locX, (double)this.u(), this.a.locZ);
    }

    public PathEntity b(BlockPosition blockposition) {
        if (this.b.getType(blockposition).isAir()) {
            BlockPosition blockposition1;
            for(blockposition1 = blockposition.down(); blockposition1.getY() > 0 && this.b.getType(blockposition1).isAir(); blockposition1 = blockposition1.down()) {
                ;
            }

            if (blockposition1.getY() > 0) {
                return super.b(blockposition1.up());
            }

            while(blockposition1.getY() < this.b.getHeight() && this.b.getType(blockposition1).isAir()) {
                blockposition1 = blockposition1.up();
            }

            blockposition = blockposition1;
        }

        if (!this.b.getType(blockposition).getMaterial().isBuildable()) {
            return super.b(blockposition);
        } else {
            BlockPosition blockposition2;
            for(blockposition2 = blockposition.up(); blockposition2.getY() < this.b.getHeight() && this.b.getType(blockposition2).getMaterial().isBuildable(); blockposition2 = blockposition2.up()) {
                ;
            }

            return super.b(blockposition2);
        }
    }

    public PathEntity a(Entity entity) {
        return this.b(new BlockPosition(entity));
    }

    private int u() {
        if (this.a.isInWater() && this.t()) {
            int i = (int)this.a.getBoundingBox().b;
            Block block = this.b.getType(new BlockPosition(MathHelper.floor(this.a.locX), i, MathHelper.floor(this.a.locZ))).getBlock();
            int j = 0;

            while(block == Blocks.WATER) {
                ++i;
                block = this.b.getType(new BlockPosition(MathHelper.floor(this.a.locX), i, MathHelper.floor(this.a.locZ))).getBlock();
                ++j;
                if (j > 16) {
                    return (int)this.a.getBoundingBox().b;
                }
            }

            return i;
        } else {
            return (int)(this.a.getBoundingBox().b + 0.5D);
        }
    }

    protected void E_() {
        super.E_();
        if (this.p) {
            if (this.b.e(new BlockPosition(MathHelper.floor(this.a.locX), (int)(this.a.getBoundingBox().b + 0.5D), MathHelper.floor(this.a.locZ)))) {
                return;
            }

            for(int i = 0; i < this.c.d(); ++i) {
                PathPoint pathpoint = this.c.a(i);
                if (this.b.e(new BlockPosition(pathpoint.a, pathpoint.b, pathpoint.c))) {
                    this.c.b(i - 1);
                    return;
                }
            }
        }

    }

    protected boolean a(Vec3D vec3d, Vec3D vec3d1, int i, int j, int k) {
        int l = MathHelper.floor(vec3d.x);
        int i1 = MathHelper.floor(vec3d.z);
        double d0 = vec3d1.x - vec3d.x;
        double d1 = vec3d1.z - vec3d.z;
        double d2 = d0 * d0 + d1 * d1;
        if (d2 < 1.0E-8D) {
            return false;
        } else {
            double d3 = 1.0D / Math.sqrt(d2);
            d0 = d0 * d3;
            d1 = d1 * d3;
            i = i + 2;
            k = k + 2;
            if (!this.a(l, (int)vec3d.y, i1, i, j, k, vec3d, d0, d1)) {
                return false;
            } else {
                i = i - 2;
                k = k - 2;
                double d4 = 1.0D / Math.abs(d0);
                double d5 = 1.0D / Math.abs(d1);
                double d6 = (double)l - vec3d.x;
                double d7 = (double)i1 - vec3d.z;
                if (d0 >= 0.0D) {
                    ++d6;
                }

                if (d1 >= 0.0D) {
                    ++d7;
                }

                d6 = d6 / d0;
                d7 = d7 / d1;
                int j1 = d0 < 0.0D ? -1 : 1;
                int k1 = d1 < 0.0D ? -1 : 1;
                int l1 = MathHelper.floor(vec3d1.x);
                int i2 = MathHelper.floor(vec3d1.z);
                int j2 = l1 - l;
                int k2 = i2 - i1;

                while(j2 * j1 > 0 || k2 * k1 > 0) {
                    if (d6 < d7) {
                        d6 += d4;
                        l += j1;
                        j2 = l1 - l;
                    } else {
                        d7 += d5;
                        i1 += k1;
                        k2 = i2 - i1;
                    }

                    if (!this.a(l, (int)vec3d.y, i1, i, j, k, vec3d, d0, d1)) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    private boolean a(int i, int j, int k, int l, int i1, int j1, Vec3D vec3d, double d0, double d1) {
        int k1 = i - l / 2;
        int l1 = k - j1 / 2;
        if (!this.b(k1, j, l1, l, i1, j1, vec3d, d0, d1)) {
            return false;
        } else {
            for(int i2 = k1; i2 < k1 + l; ++i2) {
                for(int j2 = l1; j2 < l1 + j1; ++j2) {
                    double d2 = (double)i2 + 0.5D - vec3d.x;
                    double d3 = (double)j2 + 0.5D - vec3d.z;
                    if (!(d2 * d0 + d3 * d1 < 0.0D)) {
                        PathType pathtype = this.o.a(this.b, i2, j - 1, j2, this.a, l, i1, j1, true, true);
                        if (pathtype == PathType.WATER) {
                            return false;
                        }

                        if (pathtype == PathType.LAVA) {
                            return false;
                        }

                        if (pathtype == PathType.OPEN) {
                            return false;
                        }

                        pathtype = this.o.a(this.b, i2, j, j2, this.a, l, i1, j1, true, true);
                        float f = this.a.a(pathtype);
                        if (f < 0.0F || f >= 8.0F) {
                            return false;
                        }

                        if (pathtype == PathType.DAMAGE_FIRE || pathtype == PathType.DANGER_FIRE || pathtype == PathType.DAMAGE_OTHER) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    private boolean b(int i, int j, int k, int l, int i1, int j1, Vec3D vec3d, double d0, double d1) {
        for(BlockPosition blockposition : BlockPosition.a(new BlockPosition(i, j, k), new BlockPosition(i + l - 1, j + i1 - 1, k + j1 - 1))) {
            double d2 = (double)blockposition.getX() + 0.5D - vec3d.x;
            double d3 = (double)blockposition.getZ() + 0.5D - vec3d.z;
            if (!(d2 * d0 + d3 * d1 < 0.0D) && !this.b.getType(blockposition).a(this.b, blockposition, PathMode.LAND)) {
                return false;
            }
        }

        return true;
    }

    public void a(boolean flag) {
        this.o.b(flag);
    }

    public void b(boolean flag) {
        this.o.a(flag);
    }

    public boolean g() {
        return this.o.c();
    }

    public void c(boolean flag) {
        this.p = flag;
    }
}