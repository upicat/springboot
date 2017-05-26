package xm.spring.boot.base.j8;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Java8Test {

	public static void main(String[] args) throws Exception {
		// 1
		Arrays.asList("a", "b", "d").forEach(e -> System.out.print(e));
		System.out.println(1);

		// 2
		Arrays.asList("a", "b", "d").forEach(e -> {
			System.out.print(e);
		});
		System.out.println(2);

		// 3
		List<String> l = Arrays.asList("a", "d", "b");
		l.sort((e1, e2) -> e1.compareTo(e2));
		l.forEach(e -> System.out.print(e));
		System.out.println(3);

		// 4
		DefaulableFactory.create(DefaulableImpl::new);
		System.out.println(4);

		// 5
		Arrays.asList("a", "b", "d").forEach(Java8Test::print);
		System.out.println(5);
		
		// 6
		Method method = Java8Test.class.getMethod( "main", String[].class );
        for( final Parameter parameter: method.getParameters() ) {
            System.out.println( "Parameter: " + parameter.getName() );
        }
        
        // 7
        final Collection< Task > tasks = Arrays.asList(
    	    new Task( Status.OPEN, 5 ),
    	    new Task( Status.OPEN, 13 ),
    	    new Task( Status.CLOSED, 8 ) 
    	);
        final double totalPoints = tasks
		   .stream()
		   .parallel()
		   .map( task -> task.getPoints() ) // or map( Task::getPoints ) 
		   .reduce( 0, Integer::sum );
		     
		System.out.println( "Total points (all tasks): " + totalPoints );
		
		final Map< Status, List< Task > > map = tasks
		    .stream()
		    .collect( Collectors.groupingBy( Task::getStatus ) );
		System.out.println( map );
		
		// 8
		final Path path = new File( "/tmp/a" ).toPath();
		try( Stream< String > lines = Files.lines( path, StandardCharsets.UTF_8 ) ) {
		    lines.onClose( () -> System.out.println("Done!") ).forEach( System.out::println );
		}

		// 9
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName( "JavaScript" );
		System.out.println( engine.getClass().getName() );
		System.out.println( "Result:" + engine.eval( "function f() { return 1; }; f() + 1;" ) );

		// 10
		String encoded = Base64.getEncoder().encodeToString("哈哈asdfa123123".getBytes( StandardCharsets.UTF_8 ));
        System.out.println( encoded );
        String decoded = new String(Base64.getDecoder().decode( encoded ),StandardCharsets.UTF_8 );
        System.out.println( decoded );
        encoded = Base64.getUrlEncoder().encodeToString("http://abc/a.do?d=10&e=a".getBytes( StandardCharsets.UTF_8 ));
        System.out.println( encoded );
        decoded = new String(Base64.getUrlDecoder().decode( encoded ),StandardCharsets.UTF_8 );
        System.out.println( decoded );
        
        // 11 jdeps
        // jdeps jar|.class|directory
        
        // 12 jjs
	}

	interface DefaulableFactory {
		// Interfaces now allow static methods
		static Defaulable create(Supplier<Defaulable> supplier) {
			return supplier.get();
		}
	}

	interface Defaulable {
		// Interfaces now allow default methods, the implementer may or
		// may not implement (override) them.
		default String notRequired() {
			return "Default implementation";
		}
	}

	static class DefaulableImpl implements Defaulable {
	}

	static void print(String s) {
		System.out.print(s);
	}
	
	private enum Status {
        OPEN, CLOSED
    };
     
    static final class Task {
        private final Status status;
        private final Integer points;
 
        Task( final Status status, final Integer points ) {
            this.status = status;
            this.points = points;
        }
         
        public Integer getPoints() {
            return points;
        }
         
        public Status getStatus() {
            return status;
        }
         
        @Override
        public String toString() {
            return String.format( "[%s, %d]", status, points );
        }
    }
}
