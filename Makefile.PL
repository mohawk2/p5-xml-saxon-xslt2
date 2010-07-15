use strict;
use warnings;

use inc::Module::Install;

unless (-e '/usr/share/java/saxon9he.jar' or -e '/usr/local/share/java/saxon9he.jar')
{
	warn "Need saxon9he.jar in /usr/share/java or /usr/local/share/java.";
	exit 0;
}

my $dist = 'XML-Saxon-XSLT2';
my $fn   = "lib/$dist.pm"; $fn =~ s#-#/#g;

name                $dist;
perl_version_from   $fn;
version_from        $fn;
abstract_from       $fn;
readme_from         $fn;
author              'Toby Inkster <tobyink@cpan.org>';
license             'perl';

test_requires       'Test::More' => '0.61';

requires            'common::sense'      => 0;
requires            'Carp'               => '1.00';
requires            'Inline::Java'       => 0;
requires            'IO::Handle'         => 0;
requires            'Scalar::Util'       => 0;
requires            'XML::LibXML'        => '1.60';

resources(
	'homepage'   => "http://search.cpan.org/dist/$dist/",
	'repository' => "http://goddamn.co.uk/viewvc/perlmods/$dist/",
	'bugtracker' => "http://rt.cpan.org/Dist/Display.html?Queue=$dist",
	);
	
write_doap_changes;
write_doap_changes_xml;

include 'Test::Signature';
auto_install;
WriteAll(
	'meta' => 1,
	'sign' => 1,
	);
