use inc::Module::Package 'RDF:standard';

unless (-e '/usr/share/java/saxon9he.jar'
    or  -e '/usr/local/share/java/saxon9he.jar'
    or  -e '/usr/share/java/saxonb.jar'
    or  -e '/usr/local/share/java/saxonb.jar')
{
	warn "Need saxon9he.jar or saxonb.jar to be installed in /usr/share/java or /usr/local/share/java.";
	exit 0;
}
