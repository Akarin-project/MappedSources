package net.minecraft.server;

import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceManagerFallback implements IResourceManager {

    private static final Logger b = LogManager.getLogger();
    protected final List<IResourcePack> a = Lists.newArrayList();
    private final EnumResourcePackType c;

    public ResourceManagerFallback(EnumResourcePackType enumresourcepacktype) {
        this.c = enumresourcepacktype;
    }

    public void a(IResourcePack iresourcepack) {
        this.a.add(iresourcepack);
    }

    public IResource a(MinecraftKey minecraftkey) throws IOException {
        this.d(minecraftkey);
        IResourcePack iresourcepack = null;
        MinecraftKey minecraftkey1 = c(minecraftkey);

        for (int i = this.a.size() - 1; i >= 0; --i) {
            IResourcePack iresourcepack1 = (IResourcePack) this.a.get(i);

            if (iresourcepack == null && iresourcepack1.b(this.c, minecraftkey1)) {
                iresourcepack = iresourcepack1;
            }

            if (iresourcepack1.b(this.c, minecraftkey)) {
                InputStream inputstream = null;

                if (iresourcepack != null) {
                    inputstream = this.a(minecraftkey1, iresourcepack);
                }

                return new Resource(iresourcepack1.a(), minecraftkey, this.a(minecraftkey, iresourcepack1), inputstream);
            }
        }

        throw new FileNotFoundException(minecraftkey.toString());
    }

    protected InputStream a(MinecraftKey minecraftkey, IResourcePack iresourcepack) throws IOException {
        InputStream inputstream = iresourcepack.a(this.c, minecraftkey);

        return (InputStream) (ResourceManagerFallback.b.isDebugEnabled() ? new ResourceManagerFallback.a(inputstream, minecraftkey, iresourcepack.a()) : inputstream);
    }

    private void d(MinecraftKey minecraftkey) throws IOException {
        if (minecraftkey.getKey().contains("..")) {
            throw new IOException("Invalid relative path to resource: " + minecraftkey);
        }
    }

    public List<IResource> b(MinecraftKey minecraftkey) throws IOException {
        this.d(minecraftkey);
        ArrayList arraylist = Lists.newArrayList();
        MinecraftKey minecraftkey1 = c(minecraftkey);
        Iterator iterator = this.a.iterator();

        while (iterator.hasNext()) {
            IResourcePack iresourcepack = (IResourcePack) iterator.next();

            if (iresourcepack.b(this.c, minecraftkey)) {
                InputStream inputstream = iresourcepack.b(this.c, minecraftkey1) ? this.a(minecraftkey1, iresourcepack) : null;

                arraylist.add(new Resource(iresourcepack.a(), minecraftkey, this.a(minecraftkey, iresourcepack), inputstream));
            }
        }

        if (arraylist.isEmpty()) {
            throw new FileNotFoundException(minecraftkey.toString());
        } else {
            return arraylist;
        }
    }

    public Collection<MinecraftKey> a(String s, Predicate<String> predicate) {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.a.iterator();

        while (iterator.hasNext()) {
            IResourcePack iresourcepack = (IResourcePack) iterator.next();

            arraylist.addAll(iresourcepack.a(this.c, s, Integer.MAX_VALUE, predicate));
        }

        Collections.sort(arraylist);
        return arraylist;
    }

    static MinecraftKey c(MinecraftKey minecraftkey) {
        return new MinecraftKey(minecraftkey.b(), minecraftkey.getKey() + ".mcmeta");
    }

    static class a extends InputStream {

        private final InputStream a;
        private final String b;
        private boolean c;

        public a(InputStream inputstream, MinecraftKey minecraftkey, String s) {
            this.a = inputstream;
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();

            (new Exception()).printStackTrace(new PrintStream(bytearrayoutputstream));
            this.b = "Leaked resource: \'" + minecraftkey + "\' loaded from pack: \'" + s + "\'\n" + bytearrayoutputstream;
        }

        public void close() throws IOException {
            this.a.close();
            this.c = true;
        }

        protected void finalize() throws Throwable {
            if (!this.c) {
                ResourceManagerFallback.b.warn(this.b);
            }

            super.finalize();
        }

        public int read() throws IOException {
            return this.a.read();
        }
    }
}
