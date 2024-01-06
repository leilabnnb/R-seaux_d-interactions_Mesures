# Définir le style de l'histogramme
set style data histogram
set style histogram cluster gap 1
set style fill solid border -1
set boxwidth 0.5
set output 'distanceHisto.png'

# Définir les intervalles des axes
set xrange [-1:21]
set yrange [1:950000000]
set logscale y

# Définir les labels des axes
set xlabel "Distance"
set ylabel "Fréquence"

# Tracer l'histogramme
plot 'distances.dat' title 'Distibution des distances' with boxes