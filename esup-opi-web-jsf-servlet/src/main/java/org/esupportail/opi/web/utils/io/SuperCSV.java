package org.esupportail.opi.web.utils.io;


import fj.P2;
import fj.Unit;
import fj.data.Stream;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static fj.P.p;
import static fj.data.Stream.cons;
import static fj.data.Stream.nil;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.esupportail.opi.web.utils.io.SuperCSV.IOUnit.ioUnit;

public abstract class SuperCSV<A extends Closeable> implements Closeable {

    private SuperCSV() {}

    public abstract A run() throws IOException;

    @Override
    public final void close() throws IOException{
        run().close();
    }
    public static SuperCSV<ICsvBeanWriter> superCSV(final Path filePath, final String... headers) throws IOException {
        return new SuperCSV<ICsvBeanWriter>() {
            final Writer writer = Files.newBufferedWriter(filePath, UTF_8);
            final ICsvBeanWriter beanWriter =
                    new CsvBeanWriter(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
            { beanWriter.writeHeader(headers); }
            public ICsvBeanWriter run() throws IOException {
                return beanWriter;
            }
        };
    }

    public static <T extends Closeable> SuperCSV<T> unit(final T t) {
        return new SuperCSV<T>() {
            public T run() throws IOException {
                return t;
            }
        };
    }

    public final <B extends Closeable> SuperCSV<B> map(final IOF<A, B> f) {
        return new SuperCSV<B>() {
            public B run() throws IOException {
                return f.fio(SuperCSV.this.run());
            }
        };
    }

    public final <B extends Closeable> SuperCSV<B> bind(final IOF<A, SuperCSV<B>> f) {
        return new SuperCSV<B>() {
            public B run() throws IOException {
                return SuperCSV.this.map(f).run().run();
            }
        };
    }

    public static <T> IOF2<P2<T, String[]>, ICsvBeanWriter, IOUnit> write_() {
        return new IOF2<P2<T, String[]>, ICsvBeanWriter, IOUnit>() {
            public IOUnit fio(P2<T, String[]> p2, ICsvBeanWriter writer) throws IOException {
                writer.write(p2._1(), p2._2());
                return ioUnit();
            }
        };
    }

    // ############ Functional types that may throw IOExceptions

    public static abstract class IOF<I, O> {
        public abstract O fio(I i) throws IOException;
    }

    public static abstract class IOF2<I, J, O> {
        public abstract O fio(I i, J j) throws IOException;
        public IOF<J, O> fio(final I i) throws IOException {
            return new IOF<J, O>() {
                public O fio(J j) throws IOException {
                    return IOF2.this.fio(i, j);
                }
            };
        }
    }

    public static final class IOUnit implements Closeable {
        private static final IOUnit iou = new IOUnit();
        private IOUnit() {}
        public static IOUnit ioUnit() { return iou; }
        public Unit toUnit() { return Unit.unit(); }
        @Override
        public void close() throws IOException {}
    }

    public static <R> IOUnit forEach(final Stream<R> rs, final IOF<R, IOUnit> f) throws IOException {
        for(Stream<R> rrs = rs; rrs.isNotEmpty() ; rrs = rrs.tail()._1())
            f.fio(rrs.head());
        return ioUnit();
    }

    public static <R, T> Stream<T> mapStream(final Stream<R> rs, final IOF<R, T> f) throws IOException {
        Stream<T> ts = nil();
        for(Stream<R> rrs = rs; rrs.isNotEmpty() ; rrs = rrs.tail()._1())
            ts = cons(f.fio(rrs.head()), p(ts));
        return ts;
    }
}
