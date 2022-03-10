import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TabloTemelComponentsPage from './tablo-temel.page-object';
import TabloTemelUpdatePage from './tablo-temel-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('TabloTemel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tabloTemelComponentsPage: TabloTemelComponentsPage;
  let tabloTemelUpdatePage: TabloTemelUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    tabloTemelComponentsPage = new TabloTemelComponentsPage();
    tabloTemelComponentsPage = await tabloTemelComponentsPage.goToPage(navBarPage);
  });

  it('should load TabloTemels', async () => {
    expect(await tabloTemelComponentsPage.title.getText()).to.match(/Tablo Temels/);
    expect(await tabloTemelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete TabloTemels', async () => {
    const beforeRecordsCount = (await isVisible(tabloTemelComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(tabloTemelComponentsPage.table);
    tabloTemelUpdatePage = await tabloTemelComponentsPage.goToCreateTabloTemel();
    await tabloTemelUpdatePage.enterData();
    expect(await isVisible(tabloTemelUpdatePage.saveButton)).to.be.false;

    expect(await tabloTemelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(tabloTemelComponentsPage.table);
    await waitUntilCount(tabloTemelComponentsPage.records, beforeRecordsCount + 1);
    expect(await tabloTemelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await tabloTemelComponentsPage.deleteTabloTemel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(tabloTemelComponentsPage.records, beforeRecordsCount);
      expect(await tabloTemelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(tabloTemelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
