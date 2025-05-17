package br.edu.utfpr.appcontatos.ui.contact.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.appcontatos.R
import br.edu.utfpr.appcontatos.data.Contact
import br.edu.utfpr.appcontatos.data.generateContacts
import br.edu.utfpr.appcontatos.data.groupByInitial
import br.edu.utfpr.appcontatos.ui.contact.composables.ContactAvatar
import br.edu.utfpr.appcontatos.ui.contact.composables.DefaultErrorContent
import br.edu.utfpr.appcontatos.ui.contact.composables.DefaultLoadingContent
import br.edu.utfpr.appcontatos.ui.contact.composables.FavoriteIconButton
import br.edu.utfpr.appcontatos.ui.theme.AppContatosTheme

@Composable
fun ContactsListScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactsListViewModel = viewModel()
) {
    if (viewModel.uiState.isLoading) {
        DefaultLoadingContent(
            text = stringResource(R.string.loading_contacts)
        )
    } else if (viewModel.uiState.hasError) {
        DefaultErrorContent(
            onTryAgainPressed = viewModel::loadContacts
        )
    } else {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                AppBar(
                    onRefreshPressed = viewModel::loadContacts
                )
             },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.add)
                    )
                }
            }
        ) { paddingValues ->
            if (viewModel.uiState.contacts.isEmpty()) {
                EmptyList(
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                List(
                    modifier = Modifier.padding(paddingValues),
                    contacts = viewModel.uiState.contacts,
                    onFavoritePressed = viewModel::toggleFavorite
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    onRefreshPressed: () -> Unit
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = {
            Text(stringResource(R.string.contacts))
        },
        actions = {
            IconButton(
                onClick = onRefreshPressed
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.refresh)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppBarPreview() {
    AppContatosTheme {
        AppBar(
            onRefreshPressed = {}
        )
    }
}

@Composable
fun EmptyList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.no_data),
            contentDescription = stringResource(R.string.no_data)
        )
        Text(
            text = stringResource(R.string.no_data),
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.no_data_hint),
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, heightDp = 600)
@Composable
fun EptyListPreview() {
    AppContatosTheme {
        EmptyList()
    }
}

@Composable
fun List(
    modifier: Modifier = Modifier,
    contacts: Map<String, List<Contact>>,
    onFavoritePressed: (Contact) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        contacts.forEach { (initial, contacts) ->
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = initial,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .padding(start = 16.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            items(contacts) { contact ->
                ContactListItem(
                    contact = contact,
                    onFavoritePressed = onFavoritePressed
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    AppContatosTheme {
        List(
            contacts = generateContacts().groupByInitial(),
            onFavoritePressed = {}
        )
    }
}

@Composable
fun ContactListItem(
    modifier: Modifier = Modifier,
    contact: Contact,
    onFavoritePressed: (Contact) -> Unit
) {
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(contact.fullName)
        },
        leadingContent = {
            ContactAvatar(
                firstName = contact.firstName,
                lastName = contact.lastName
            )
        },
        trailingContent = {
            FavoriteIconButton(
                isFavorite = contact.isFavorite,
                onPressed = { onFavoritePressed(contact) }
            )
        }
    )
}