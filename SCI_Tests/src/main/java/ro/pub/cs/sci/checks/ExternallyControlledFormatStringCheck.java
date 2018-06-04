package ro.pub.cs.sci.checks;

import com.google.common.collect.Lists;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.model.declaration.VariableTreeImpl;
import org.sonar.java.model.expression.BinaryExpressionTreeImpl;
import org.sonar.java.model.expression.IdentifierTreeImpl;
import org.sonar.java.model.expression.MemberSelectExpressionTreeImpl;
import org.sonar.java.model.expression.MethodInvocationTreeImpl;
import org.sonar.java.resolve.JavaSymbol;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;

import java.util.ArrayList;
import java.util.List;

@Rule(
        key = ExternallyControlledFormatStringCheck.RULE_KEY,
        name = ExternallyControlledFormatStringCheck.RULE_MESSAGE,
        priority = Priority.MAJOR,
        tags = "SCI"
)
public class ExternallyControlledFormatStringCheck extends IssuableSubscriptionVisitor {

    protected static final String RULE_KEY = "CWE-134";
    protected static final String RULE_MESSAGE = "Don't use format strings that may be externally-controlled";

    public static final String JAVA_LANG_STRING = "java.lang.String";

    public static final String JAVA_UTIL_FORMATTER = "java.util.Formatter";
    public static final String JAVA_TEXT_MESSAGEFORMAT = "java.text.MessageFormat";

    public static final String PRINT_METHOD_NAME = "print";
    public static final String PRINTF_METHOD_NAME = "printf";
    public static final String JAVA_IO_PRINTSTREAM = "java.io.PrintStream";
    public static final String JAVA_IO_CONSOLE = "java.io.Console";
    public static final String FORMAT_METHOD_NAME = "format";
    public static final String JAVAX_SERVLET_HTTP_HTTP_SERVLET_REQUEST = "javax.servlet.http.HttpServletRequest";
    public static final String GET_PARAMETER = "getParameter";

    public List<Tree.Kind> nodesToVisit() {
        return Lists.newArrayList(Tree.Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        MethodInvocationTreeImpl methodInvocationTree = (MethodInvocationTreeImpl) tree;

        if (methodInvocationTree.methodSelect() instanceof MemberSelectExpressionTreeImpl) {
            MemberSelectExpressionTreeImpl memberSelectExpressionTree = (MemberSelectExpressionTreeImpl) methodInvocationTree.methodSelect();
            String fullyQualifiedName = ((JavaSymbol.TypeJavaSymbol) methodInvocationTree.symbol().owner()).getFullyQualifiedName();

            String methodName = memberSelectExpressionTree.identifier().name();

            if (    fullyQualifiedName.equals(JAVA_LANG_STRING) && methodName.equals(FORMAT_METHOD_NAME) ||
                    fullyQualifiedName.equals(JAVA_UTIL_FORMATTER) && methodName.equals(FORMAT_METHOD_NAME) ||
                    fullyQualifiedName.equals(JAVA_IO_PRINTSTREAM) && methodName.equals(FORMAT_METHOD_NAME) ||
                    fullyQualifiedName.equals(JAVA_IO_PRINTSTREAM) && methodName.equals(PRINTF_METHOD_NAME) ||
                    fullyQualifiedName.equals(JAVA_IO_PRINTSTREAM) && methodName.equals(PRINT_METHOD_NAME) ||
                    fullyQualifiedName.equals(JAVA_IO_PRINTSTREAM) && methodName.equals(FORMAT_METHOD_NAME) ||
                    fullyQualifiedName.equals(JAVA_IO_PRINTSTREAM) && methodName.equals(PRINTF_METHOD_NAME) ||
                    fullyQualifiedName.equals(JAVA_IO_PRINTSTREAM) && methodName.equals(PRINT_METHOD_NAME) ||
                    fullyQualifiedName.equals(JAVA_IO_CONSOLE) && methodName.equals(FORMAT_METHOD_NAME) ||
                    fullyQualifiedName.equals(JAVA_IO_CONSOLE) && methodName.equals(PRINTF_METHOD_NAME) ||
                    fullyQualifiedName.equals(JAVA_IO_CONSOLE) && methodName.equals(PRINT_METHOD_NAME) ||
                    fullyQualifiedName.equals(JAVA_TEXT_MESSAGEFORMAT) && methodName.equals(FORMAT_METHOD_NAME)
                    ) {
                onMethodInvocationFound(methodInvocationTree);
            }
        }
        super.visitNode(tree);
    }

    protected void onMethodInvocationFound(MethodInvocationTree tree) {
        Arguments args = tree.arguments();
        List<IdentifierTreeImpl> items = new ArrayList<IdentifierTreeImpl>();

        for (int i = 0; i < args.size(); i++) {
            ExpressionTree arg = args.get(i);
            aggregateRuleViolations(items, arg);
        }

        for (IdentifierTreeImpl identifierTree : items) {
            boolean isBinaryExpressionTree = identifierTree.parent() instanceof BinaryExpressionTreeImpl;
            if (isBinaryExpressionTree && isConcatenation((ExpressionTree) identifierTree.parent())) {
                reportIssue(identifierTree, RULE_MESSAGE);
            }
        }
    }

    private void aggregateRuleViolations(List<IdentifierTreeImpl> response, ExpressionTree treeOperand) {
        if (isConcatenation(treeOperand)) {
            aggregateRuleViolations(response, (BinaryExpressionTree) treeOperand);
        } else {
            if (treeOperand instanceof IdentifierTreeImpl) {
                String instanceClassName = getInitializerClassName(treeOperand);
                if (instanceClassName != null && instanceClassName.equals(JAVAX_SERVLET_HTTP_HTTP_SERVLET_REQUEST)) {
                    IdentifierTreeImpl identifier = (IdentifierTreeImpl) treeOperand;
                    VariableTreeImpl declaration = (VariableTreeImpl) identifier.symbol().declaration();

                    if (declaration.initializer() instanceof MethodInvocationTreeImpl) {
                        MethodInvocationTreeImpl initializer = (MethodInvocationTreeImpl) declaration.initializer();
                        if (initializer.methodSelect() instanceof MemberSelectExpressionTreeImpl) {
                            if (((MemberSelectExpressionTreeImpl) initializer.methodSelect()).identifier().name().equals(GET_PARAMETER)) {
                                response.add(identifier);
                            }
                        }
                    }
                }
            }
        }
    }

    private void aggregateRuleViolations(List<IdentifierTreeImpl> response, BinaryExpressionTree tree) {
        aggregateRuleViolations(response, tree.leftOperand());
        aggregateRuleViolations(response, tree.rightOperand());
    }

    private boolean isConcatenation(ExpressionTree tree) {
        return tree.kind().compareTo(Tree.Kind.PLUS) == 0;
    }

    private String getInitializerClassName(ExpressionTree treeOperand) {
        String name = null;
        try {
            IdentifierTreeImpl identifierTree = (IdentifierTreeImpl) treeOperand;
            VariableTreeImpl declaration = (VariableTreeImpl) identifierTree.symbol().declaration();
            MethodInvocationTreeImpl methodInvocationInitializer = (MethodInvocationTreeImpl) declaration.initializer();
            ExpressionTree expression = ((MemberSelectExpressionTreeImpl) methodInvocationInitializer.methodSelect()).expression();
            JavaSymbol.TypeJavaSymbol classSymbol = (JavaSymbol.TypeJavaSymbol) (((IdentifierTreeImpl) expression).symbol().type()).symbol();
            name = classSymbol.getFullyQualifiedName();
        } catch (Exception e) {
        }
        return name;
    }
}
